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
class JsBundle(val module: String, val file: String, val text: String, val dependencies: Set<String>)

class JsExported(val kind: String, val name: String, val text: String, val bundle: JsBundle)

val jsExported = Regex(
    """^export (?:declare )?(?:abstract )?(class|interface|namespace|function|const|let|var|enum|type) ([A-Za-z0-9_$]+)""",
    RegexOption.MULTILINE,
)

fun isJsDeclarationFile(name: String) = name.endsWith(".d.mts")

fun JsBundle.exported(): List<JsExported> {
    val found = jsExported.findAll(text).toList()
    return found.mapIndexed { index, match ->
        val end = found.getOrNull(index + 1)?.range?.first ?: text.length
        JsExported(match.groupValues[1], match.groupValues[2], text.substring(match.range.first, end).trimEnd(), this)
    }
}

/** The names exported more than once, each with the message that explains where. */
fun jsExportNameClashes(bundles: List<JsBundle>): Map<String, String> =
    bundles.flatMap { it.exported() }.groupBy { it.name }.filter { (_, all) ->
        val (namespaces, others) = all.partition { it.kind == "namespace" }
        val oncePerFile = all.groupBy { it.bundle }.values.all { inFile ->
            inFile.count { it.kind == "namespace" } <= 1 && inFile.count { it.kind != "namespace" } <= 1
        }
        val oneDeclaration = others.map { it.text }.distinct().size <= 1 &&
            namespaces.map { it.text }.distinct().size <= 1
        val companion = namespaces.isEmpty() || (others.isNotEmpty() && others.all { it.kind in setOf("class", "interface") })
        !(oncePerFile && oneDeclaration && companion)
    }.toSortedMap().mapValues { (name, all) ->
        "$name: " + all.groupBy { it.kind to it.text }.values.joinToString(", ") { found ->
            "${found.first().kind} in ${found.first().bundle.file}" + if (found.size > 1) " (+${found.size - 1})" else ""
        }
    }

val jsExportNameClashCheck = tasks.register("jsExportNameClashCheck") {
    group = "verification"
    description = "Fails when two declarations exported to JavaScript share a name."
    val declarationFiles = files()
    subprojects {
        plugins.withId("org.jetbrains.kotlin.multiplatform") {
            declarationFiles.from(tasks.matching { it.name == "compileProductionLibraryKotlinJs" })
        }
    }
    val declarations = declarationFiles.asFileTree.matching { include("**/*.d.mts") }
    val root = rootDir
    val marker = layout.buildDirectory.file("jsExportNameClashCheck/checked")
    inputs.files(declarations)
    outputs.file(marker)

    doLast {
        val files = declarations.files.sortedBy { it.path }
        if (files.isEmpty()) {
            throw GradleException("No `.d.mts` found: the Kotlin/JS production libraries were not linked.")
        }
        val clashes = jsExportNameClashes(files.map { JsBundle(it.path, it.relativeTo(root).path, it.readText(), emptySet()) })
        if (clashes.isNotEmpty()) {
            throw GradleException(
                "Declarations exported to JavaScript share a name; a consumer's `.d.mts` declares each of them twice:" +
                    clashes.values.joinToString("") { "\n  $it" } +
                    "\nRename one of them, or give it a distinct @JsName."
            )
        }
        marker.get().asFile.apply { parentFile.mkdirs() }.writeText(files.joinToString("\n") { it.relativeTo(root).path })
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
subprojects {
    plugins.withId("org.jetbrains.kotlin.multiplatform") {
        tasks.matching { it.name == "check" }.configureEach { dependsOn(jsExportNameClashCheck) }
    }
}
