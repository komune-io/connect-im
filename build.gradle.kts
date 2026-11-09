plugins {
    alias(catalogue.plugins.f2.bom)
    alias(catalogue.plugins.kotlin.spring) apply false
    alias(catalogue.plugins.kotlin.serialization) apply false
    alias(catalogue.plugins.kotlin.kapt) apply false
    alias(catalogue.plugins.spring.boot) apply false

    alias(catalogue.plugins.fixers.gradle.config)
    alias(catalogue.plugins.fixers.gradle.check)

    alias(catalogue.plugins.fixers.gradle.kotlin.mpp) apply false
    alias(catalogue.plugins.fixers.gradle.kotlin.jvm) apply false
    alias(catalogue.plugins.fixers.gradle.publish)
    alias(catalogue.plugins.fixers.gradle.npm) apply false
}

fixers {
    bundle {
        group = "io.komune.im"
        name = "IM"
        description = "Identity Management"
        url = "https://github.com/komune-io/connect-im"
    }
    kt2Ts {
        outputDirectory = "ts/"
    }
    npm {
        organization = "komune-io"
    }
    sonar {
        organization = "komune-io"
        projectKey = "komune-io_connect-im"
    }
    repositories {
        sonatypeSnapshots = true
    }
}

plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().resolution("ajv", "8.20.0")
}

// Kotlin/JS ES modules export a declaration under its simple name, without its package, and a
// consumer that bundles several IM modules gets all of them in one `.d.mts`. The compiler lets two
// exported declarations share a name; TypeScript then merges them, or fails on `interface X extends X`.
// A class or an interface may share its name with one namespace: that pair is a Kotlin companion.
// Every bundle repeats its dependencies' declarations, IM's and those of external libraries alike;
// the `@JsExport` of each module's sources tells IM's own apart.
class JsModule(
    val name: String,
    val dependencies: Set<String>,
    val sources: Map<String, String>,
    val declarationFiles: Map<String, String>,
)

class JsExported(val kind: String, val name: String, val text: String, val module: String, val file: String)

val jsExported = Regex(
    """^export (?:declare )?(?:abstract )?(class|interface|namespace|function|const|let|var|enum|type) ([A-Za-z0-9_$]+)""",
    RegexOption.MULTILINE,
)
val kotlinJsExport = Regex(
    """^@JsExport((?:\s+@\w+(?:\([^)]*\))?)*)\s+(?:(?:public|data|sealed|abstract|open|enum|expect|actual|value|inline)\s+)*""" +
        """(?:class|interface|object|fun|val|var|const\s+val)\s+([A-Za-z0-9_]+)""",
    RegexOption.MULTILINE,
)
val kotlinJsName = Regex("""@JsName\("([^"]+)"\)""")

fun isJsDeclarationFile(name: String) = name.endsWith(".d.mts") || name.endsWith(".d.ts")

/** The names exported more than once, each with the message that explains where. */
fun jsExportNameClashes(modules: List<JsModule>): Map<String, String> {
    val byName = modules.associateBy { it.name }
    fun dependenciesOf(module: String, seen: MutableSet<String> = mutableSetOf()): Set<String> {
        byName[module]?.dependencies.orEmpty().filter { seen.add(it) }.forEach { dependenciesOf(it, seen) }
        return seen
    }
    val problems = sortedMapOf<String, MutableList<String>>()
    fun report(name: String, problem: String) = problems.getOrPut(name) { mutableListOf() }.add(problem)

    val owners = mutableMapOf<String, MutableSet<String>>()
    modules.forEach { module ->
        module.sources.forEach { (file, text) ->
            val found = kotlinJsExport.findAll(text).toList()
            val annotated = Regex("^@JsExport\\b", RegexOption.MULTILINE).findAll(text).count()
            if (found.size != annotated) {
                report(file, "$file: $annotated @JsExport, ${found.size} read; write the annotation alone on the line above the declaration")
            }
            found.forEach { match ->
                val name = kotlinJsName.find(match.groupValues[1])?.groupValues?.get(1) ?: match.groupValues[2]
                owners.getOrPut(name) { mutableSetOf() }.add(module.name)
            }
        }
    }
    val exported = modules.flatMap { module ->
        module.declarationFiles.flatMap { (file, text) ->
            val found = jsExported.findAll(text).toList()
            found.mapIndexed { index, match ->
                val end = found.getOrNull(index + 1)?.range?.first ?: text.length
                JsExported(match.groupValues[1], match.groupValues[2], text.substring(match.range.first, end).trimEnd(), module.name, file)
            }
        }
    }.groupBy { it.name }

    exported.forEach { (name, all) ->
        all.groupBy { it.file }.forEach { (file, inFile) ->
            val (namespaces, others) = inFile.partition { it.kind == "namespace" }
            val companion = namespaces.size <= 1 && others.size <= 1 &&
                (namespaces.isEmpty() || others.isEmpty() || others.single().kind in setOf("class", "interface"))
            if (!companion) report(name, inFile.joinToString(" and ") { it.kind } + " in $file")
        }
        all.partition { it.kind == "namespace" }.toList().forEach { group ->
            val texts = group.groupBy { it.text }.values
            if (texts.size > 1) report(name, texts.joinToString(" vs ") { "${it.first().kind} in ${it.first().file}" + if (it.size > 1) " (+${it.size - 1})" else "" })
        }
    }
    owners.forEach { (name, owning) ->
        if (owning.size > 1) {
            report(name, "@JsExport in ${owning.sorted().joinToString(" and ")}")
        }
        owning.forEach { owner ->
            val module = byName.getValue(owner)
            val reached = setOf(owner) + modules.filter { owner in dependenciesOf(it.name) }.map { it.name }
            if (module.declarationFiles.isNotEmpty() && exported[name].orEmpty().none { it.module == owner }) {
                report(name, "@JsExport in $owner, absent from its declaration files")
            }
            exported[name].orEmpty().filter { it.module !in reached }.map { it.file }.distinct().forEach {
                report(name, "@JsExport in $owner, and also in $it, which does not depend on it")
            }
        }
    }
    return problems.mapValues { (name, found) -> "$name: " + found.joinToString("; ") }
}

val jsModules = mutableMapOf<String, Triple<FileTree, FileTree, Provider<Set<String>>>>()
subprojects {
    plugins.withId("org.jetbrains.kotlin.multiplatform") {
        val link = tasks.matching { it.name == "compileProductionLibraryKotlinJs" }
        val dependencies = provider {
            configurations.filter { it.name in setOf("commonMainApi", "commonMainImplementation", "jsMainApi", "jsMainImplementation") }
                .flatMap { it.dependencies.withType<ProjectDependency>() }
                .map { it.path }
                .toSet()
        }
        jsModules[path] = Triple(
            files(link).asFileTree.matching { include("**/*.d.mts", "**/*.d.ts") },
            fileTree("src").matching { include("commonMain/kotlin/**/*.kt", "jsMain/kotlin/**/*.kt") },
            dependencies,
        )
    }
}

val jsExportNameClashCheck = tasks.register("jsExportNameClashCheck") {
    group = "verification"
    description = "Fails when two declarations exported to JavaScript share a name."
    val root = rootDir
    val marker = layout.buildDirectory.file("jsExportNameClashCheck/checked")
    inputs.files(jsModules.values.map { it.first })
    inputs.files(jsModules.values.map { it.second })
    outputs.file(marker)

    doLast {
        fun FileTree.read() = files.sortedBy { it.path }.associate { it.relativeTo(root).path to it.readText() }
        val modules = jsModules.toSortedMap().map { (path, module) ->
            JsModule(path, module.third.get(), module.second.read(), module.first.read())
        }
        if (modules.all { it.declarationFiles.isEmpty() }) {
            throw GradleException("No `.d.mts` found: the Kotlin/JS production libraries were not linked.")
        }
        val clashes = jsExportNameClashes(modules)
        if (clashes.isNotEmpty()) {
            throw GradleException(
                "Declarations exported to JavaScript share a name; a consumer's `.d.mts` declares each of them twice:" +
                    clashes.values.joinToString("") { "\n  $it" } +
                    "\nRename one of them, or give it a distinct @JsName."
            )
        }
        marker.get().asFile.apply { parentFile.mkdirs() }
            .writeText(modules.flatMap { it.declarationFiles.keys }.joinToString("\n"))
    }
}

gradle.taskGraph.whenReady {
    if (hasTask(":${jsExportNameClashCheck.name}") && allTasks.none { it.name == "compileProductionLibraryKotlinJs" }) {
        throw GradleException("jsExportNameClashCheck has no Kotlin/JS production library to read.")
    }
}

// Each case is a directory with, per module, its declaration file and its Kotlin source (`<module>.kt`);
// `dependencies` lists `module: dependency` lines, `expected` the names that must be reported.
val jsExportNameClashSelfTest = tasks.register("jsExportNameClashSelfTest") {
    group = "verification"
    description = "Runs jsExportNameClashCheck's rule on the cases under gradle/js-export-name-clash."
    val cases = layout.projectDirectory.dir("gradle/js-export-name-clash")
    val marker = layout.buildDirectory.file("jsExportNameClashSelfTest/passed")
    inputs.dir(cases)
    outputs.file(marker)

    doLast {
        val directories = cases.asFile.listFiles().orEmpty().filter { it.isDirectory }.sortedBy { it.name }
        if (directories.isEmpty()) {
            throw GradleException("No case found under gradle/js-export-name-clash.")
        }
        val failures = directories.mapNotNull { case ->
            val dependencies = case.resolve("dependencies").takeIf { it.isFile }?.readLines().orEmpty()
                .filter { it.isNotBlank() }
                .map { it.split(":").map(String::trim) }
                .groupBy({ it[0] }, { it[1] })
            val files = case.listFiles().orEmpty().sortedBy { it.name }
            val modules = files.filter { isJsDeclarationFile(it.name) || it.name.endsWith(".kt") }
                .groupBy { it.name.substringBefore(".") }
                .map { (module, moduleFiles) ->
                    val (sources, declarations) = moduleFiles.partition { it.name.endsWith(".kt") }
                    JsModule(
                        module,
                        dependencies[module].orEmpty().toSet(),
                        sources.associate { "${case.name}/${it.name}" to it.readText() },
                        declarations.associate { "${case.name}/${it.name}" to it.readText() },
                    )
                }
            val expected = case.resolve("expected").readLines().filter { it.isNotBlank() }.toSet()
            val actual = jsExportNameClashes(modules)
            if (actual.keys == expected) null else "${case.name}: expected $expected, reported ${actual.values}"
        }
        if (failures.isNotEmpty()) {
            throw GradleException("jsExportNameClashCheck's rule failed on:" + failures.joinToString("") { "\n  $it" })
        }
        marker.get().asFile.apply { parentFile.mkdirs() }.writeText("ok")
    }
}

tasks.matching { it.name == "check" }.configureEach { dependsOn(jsExportNameClashSelfTest, jsExportNameClashCheck) }
