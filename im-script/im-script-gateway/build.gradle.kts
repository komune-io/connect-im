plugins {
    id("org.springframework.boot")
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
//    id("org.graalvm.buildtools.native")
}

dependencies {
    implementation(project(Modules.Script.init))
    implementation(project(Modules.Script.spaceConfig))
    implementation(project(Modules.Script.spaceCreate))

    Dependencies.Jvm.f2(::implementation)
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    imageName.set("komune-io/im-script:${this.project.version}")
}
