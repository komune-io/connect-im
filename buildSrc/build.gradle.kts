import java.util.Properties

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
    implementation("city.smartb.fixers.gradle:dependencies:0.16.0")
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
