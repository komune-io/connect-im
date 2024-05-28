import java.util.Properties

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("io.komune.fixers.gradle:dependencies:0.19.0-SNAPSHOT")
}

repositories {
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://s01.oss.sonatype.org/service/local/repositories/releases/content") }
    maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots") }
}


loadGradleProperties()
fun Project.loadGradleProperties() {
  File("${project.rootProject.rootDir}/../gradle.properties").inputStream().use { stream ->
    val props = Properties()
    props.load(stream)
    props.forEach { (key, value) ->
      System.setProperty(key.toString(), value.toString())
    }
  }
}
