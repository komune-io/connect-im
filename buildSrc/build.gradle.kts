import java.util.Properties

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("io.komune.fixers.gradle:dependencies:0.17.0-SNAPSHOT")
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
