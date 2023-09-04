import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.10"
    `maven-publish`
    `java-gradle-plugin`
}

group = "com.example.gp.pluginId"
version = "1"

dependencies {
    implementation(kotlin("gradle-plugin-api"))

    // tests
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

gradlePlugin {
    val allowTestReturn by plugins.creating {
        id = group as String
        implementationClass = "com.example.gp.AllowTestReturnGradlePlugin"
    }
}

publishing {
    publications.withType<MavenPublication>() {
        artifactId = "${project.group}.gradle.plugin"
    }
}