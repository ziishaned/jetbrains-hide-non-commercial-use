import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.2.21"
    id("org.jetbrains.intellij.platform") version "2.15.0"
}

group = "dev.zeeshan.jetbrains"
version = "0.1.3"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.3")
    testRuntimeOnly("junit:junit:4.13.2")

    intellijPlatform {
        intellijIdeaCommunity("2025.2.6.1")
    }
}

intellijPlatform {
    buildSearchableOptions = false

    pluginConfiguration {
        id = "dev.zeeshan.jetbrains.hide-non-commercial-use"
        name = "Hide Non-commercial Use Status"
        version = project.version.toString()

        ideaVersion {
            sinceBuild = "252"
        }

        changeNotes = """
            Initial version.
        """.trimIndent()
    }
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    test {
        useJUnitPlatform()
    }
}
