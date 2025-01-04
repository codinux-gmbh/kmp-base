buildscript {
    val kotlinVersion: String by extra

    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

        classpath("com.android.tools.build:gradle:8.5.2")
    }
}


allprojects {
    group = "net.codinux.codinux"
    version = "0.5.0-SNAPSHOT"

    ext["sourceCodeRepositoryBaseUrl"] = "github.com/codinux-gmbh/kmp-base"

    ext["projectDescription"] = "Common functions and classes for all KMP platforms that are missing in Kotlin Stdlib like concurrent structures, URL, Locale, ..."

    repositories {
        mavenCentral()
        google()
    }
}