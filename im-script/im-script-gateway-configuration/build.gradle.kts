plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
}

dependencies {
    implementation(project(Modules.Script.imScriptFunctionConfig))
    Dependencies.Jvm.f2(::implementation)
    Dependencies.Jvm.slf4j(::implementation)
}
