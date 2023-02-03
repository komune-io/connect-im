plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
}

dependencies {
    implementation("city.smartb.fixers.gradle:dependencies:0.12.0")
}
