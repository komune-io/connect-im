import java.util.Properties

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("io.komune.fixers.gradle:dependencies:0.26.0-SNAPSHOT")
}

repositories {
    mavenCentral()
    maven { url = uri("https://central.sonatype.com/repository/maven-snapshots") }
    if(System.getenv("MAVEN_LOCAL_USE") == "true") {
        mavenLocal()
    }
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
