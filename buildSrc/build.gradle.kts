plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
}

dependencies {
    implementation("city.smartb.fixers.gradle:dependencies:next-SNAPSHOT")
}
