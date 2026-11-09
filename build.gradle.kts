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
// A bundle also carries its dependencies' declarations: those are the dependency's, not a second one.
class JsBundle(val module: String, val file: String, val text: String, val dependencies: Set<String>)

class JsExported(val kind: String, val name: String, val text: String, val bundle: JsBundle)

val jsExported = Regex(
    """^export (?:declare )?(?:abstract )?(class|interface|namespace|function|const|let|var|enum|type) ([A-Za-z0-9_$]+)""",
    RegexOption.MULTILINE,
)

fun isJsDeclarationFile(name: String) = name.endsWith(".d.mts") || name.endsWith(".d.ts")

fun JsBundle.exported(): List<JsExported> {
    val found = jsExported.findAll(text).toList()
    return found.mapIndexed { index, match ->
        val end = found.getOrNull(index + 1)?.range?.first ?: text.length
        JsExported(match.groupValues[1], match.groupValues[2], text.substring(match.range.first, end).trimEnd(), this)
    }
}

/** The names exported more than once, each with the message that explains where. */
fun jsExportNameClashes(bundles: List<JsBundle>): Map<String, String> {
    val exported = bundles.associateWith { it.exported() }
    val byModule = bundles.groupBy { it.module }
    fun dependenciesOf(module: String, seen: MutableSet<String> = mutableSetOf()): Set<String> {
        byModule[module].orEmpty().flatMap { it.dependencies }.filter { seen.add(it) }.forEach { dependenciesOf(it, seen) }
        return seen
    }
    val own = bundles.flatMap { bundle ->
        val inherited = dependenciesOf(bundle.module).flatMap { byModule[it].orEmpty() }
            .flatMap { exported.getValue(it) }.map { Triple(it.kind, it.name, it.text) }.toSet()
        exported.getValue(bundle).filter { Triple(it.kind, it.name, it.text) !in inherited }
    }.groupBy { it.name }

    return exported.values.flatten().groupBy { it.name }.filter { (name, all) ->
        val companionInEachFile = all.groupBy { it.bundle }.values.all { inFile ->
            val (namespaces, others) = inFile.partition { it.kind == "namespace" }
            namespaces.size <= 1 && others.size <= 1 &&
                (namespaces.isEmpty() || others.isEmpty() || others.single().kind in setOf("class", "interface"))
        }
        val oneModule = own[name].orEmpty().map { it.bundle.module }.distinct().size <= 1
        all.size >= 2 && !(companionInEachFile && oneModule)
    }.toSortedMap().mapValues { (name, all) ->
        "$name: " + all.groupBy { it.kind to it.text }.values.joinToString(", ") { found ->
            "${found.first().kind} in ${found.first().bundle.file}" + if (found.size > 1) " (+${found.size - 1})" else ""
        }
    }
}

val jsModules = mutableMapOf<String, Pair<FileTree, Provider<Set<String>>>>()
subprojects {
    plugins.withId("org.jetbrains.kotlin.multiplatform") {
        val link = tasks.matching { it.name == "compileProductionLibraryKotlinJs" }
        val dependencies = provider {
            configurations.filter { it.name in setOf("commonMainApi", "commonMainImplementation", "jsMainApi", "jsMainImplementation") }
                .flatMap { it.dependencies.withType<ProjectDependency>() }
                .map { it.path }
                .toSet()
        }
        jsModules[path] = files(link).asFileTree.matching { include("**/*.d.mts", "**/*.d.ts") } to dependencies
    }
}

val jsExportNameClashCheck = tasks.register("jsExportNameClashCheck") {
    group = "verification"
    description = "Fails when two declarations exported to JavaScript share a name."
    val root = rootDir
    val marker = layout.buildDirectory.file("jsExportNameClashCheck/checked")
    inputs.files(jsModules.values.map { it.first })
    outputs.file(marker)

    doLast {
        val bundles = jsModules.toSortedMap().flatMap { (module, declarations) ->
            declarations.first.files.sortedBy { it.path }
                .map { JsBundle(module, it.relativeTo(root).path, it.readText(), declarations.second.get()) }
        }
        if (bundles.isEmpty()) {
            throw GradleException("No `.d.mts` found: the Kotlin/JS production libraries were not linked.")
        }
        val clashes = jsExportNameClashes(bundles)
        if (clashes.isNotEmpty()) {
            throw GradleException(
                "Declarations exported to JavaScript share a name; a consumer's `.d.mts` declares each of them twice:" +
                    clashes.values.joinToString("") { "\n  $it" } +
                    "\nRename one of them, or give it a distinct @JsName."
            )
        }
        marker.get().asFile.apply { parentFile.mkdirs() }.writeText(bundles.joinToString("\n") { it.file })
    }
}

gradle.taskGraph.whenReady {
    if (hasTask(":${jsExportNameClashCheck.name}") && allTasks.none { it.name == "compileProductionLibraryKotlinJs" }) {
        throw GradleException("jsExportNameClashCheck has no Kotlin/JS production library to read.")
    }
}

// Each case is a directory of declaration files, one per module; `dependencies` lists `module: dependency`
// lines, `expected` the names that must be reported.
val jsExportNameClashSelfTest = tasks.register("jsExportNameClashSelfTest") {
    group = "verification"
    description = "Runs jsExportNameClashCheck's rule on the cases under gradle/js-export-name-clash."
    val cases = layout.projectDirectory.dir("gradle/js-export-name-clash")
    val marker = layout.buildDirectory.file("jsExportNameClashSelfTest/passed")
    inputs.dir(cases)
    outputs.file(marker)

    doLast {
        val failures = cases.asFile.listFiles().orEmpty().filter { it.isDirectory }.sortedBy { it.name }.mapNotNull { case ->
            val dependencies = case.resolve("dependencies").takeIf { it.isFile }?.readLines().orEmpty()
                .filter { it.isNotBlank() }
                .map { it.split(":").map(String::trim) }
                .groupBy({ it[0] }, { it[1] })
            val bundles = case.listFiles().orEmpty().filter { isJsDeclarationFile(it.name) }.sortedBy { it.name }.map {
                val module = it.name.substringBefore(".")
                JsBundle(module, "${case.name}/${it.name}", it.readText(), dependencies[module].orEmpty().toSet())
            }
            val expected = case.resolve("expected").readLines().filter { it.isNotBlank() }.toSet()
            val actual = jsExportNameClashes(bundles).keys
            if (actual == expected) null else "${case.name}: expected $expected, reported $actual"
        }
        if (failures.isEmpty() && cases.asFile.listFiles().orEmpty().none { it.isDirectory }) {
            throw GradleException("No case found under gradle/js-export-name-clash.")
        }
        if (failures.isNotEmpty()) {
            throw GradleException("jsExportNameClashCheck's rule failed on:" + failures.joinToString("") { "\n  $it" })
        }
        marker.get().asFile.apply { parentFile.mkdirs() }.writeText("ok")
    }
}

tasks.matching { it.name == "check" }.configureEach { dependsOn(jsExportNameClashSelfTest, jsExportNameClashCheck) }
