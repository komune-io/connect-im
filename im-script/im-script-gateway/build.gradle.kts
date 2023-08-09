plugins {
    id("org.springframework.boot")
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
//    id("org.graalvm.buildtools.native")
}

dependencies {
    implementation(project(Modules.Script.imScriptConfig))
    implementation(project(Modules.Script.imKeycloakF2ScriptConfigCommand))
    implementation(project(Modules.Script.imKeycloakF2ScriptInitCommand))


    Dependencies.Jvm.f2(::implementation)
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    imageName.set("smartbcity/im-script:${this.project.version}")
}
