pluginManagement {
    val kotlinVersion: String by settings

    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("multiplatform") version kotlinVersion apply false

        kotlin("plugin.allopen") version kotlinVersion apply false
        kotlin("plugin.noarg") version kotlinVersion apply false
    }
}


plugins {
    // Use the Foojay Toolchains plugin to automatically download JDKs required by subprojects.
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}


rootProject.name = "kmp-base-project"


include("kmp-base")

include("kmp-base-text")
