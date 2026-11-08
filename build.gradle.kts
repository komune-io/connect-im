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
        val exported = Regex(
            """^export (?:declare )?(?:abstract )?(class|interface|namespace|function|const|let|var|enum|type) ([A-Za-z0-9_$]+)""",
            RegexOption.MULTILINE,
        )
        val files = declarations.files.sortedBy { it.path }
        if (files.isEmpty()) {
            throw GradleException("No `.d.mts` found: the Kotlin/JS production libraries were not linked.")
        }
        // name to (kind, declaration text, file)
        val byName = files.flatMap { file ->
            val text = file.readText()
            val found = exported.findAll(text).toList()
            found.mapIndexed { index, match ->
                val end = found.getOrNull(index + 1)?.range?.first ?: text.length
                match.groupValues[2] to Triple(
                    match.groupValues[1],
                    text.substring(match.range.first, end).trimEnd(),
                    file.relativeTo(root).path,
                )
            }
        }.groupBy({ it.first }, { it.second })

        val clashes = byName.filter { (_, all) ->
            val (namespaces, others) = all.partition { it.first == "namespace" }
            val oncePerFile = all.groupBy { it.third }.values.all { inFile ->
                inFile.count { it.first == "namespace" } <= 1 && inFile.count { it.first != "namespace" } <= 1
            }
            val oneDeclaration = others.map { it.second }.distinct().size <= 1 &&
                namespaces.map { it.second }.distinct().size <= 1
            val companion = namespaces.isEmpty() || (others.isNotEmpty() && others.all { it.first in setOf("class", "interface") })
            !(oncePerFile && oneDeclaration && companion)
        }

        if (clashes.isNotEmpty()) {
            throw GradleException(
                "Declarations exported to JavaScript share a name; a consumer's `.d.mts` declares each of them twice:" +
                    clashes.entries.sortedBy { it.key }.joinToString("") { (name, all) ->
                        "\n  $name: " + all.groupBy { it.first to it.second }.entries.joinToString(", ") { (declaration, found) ->
                            "${declaration.first} in ${found.first().third}" + if (found.size > 1) " (+${found.size - 1})" else ""
                        }
                    } +
                    "\nRename one of them, or give it a distinct @JsName."
            )
        }
        marker.get().asFile.apply { parentFile.mkdirs() }.writeText(files.joinToString("\n") { it.relativeTo(root).path })
    }
}

tasks.matching { it.name == "check" }.configureEach { dependsOn(jsExportNameClashCheck) }
subprojects {
    plugins.withId("org.jetbrains.kotlin.multiplatform") {
        tasks.matching { it.name == "check" }.configureEach { dependsOn(jsExportNameClashCheck) }
    }
}
