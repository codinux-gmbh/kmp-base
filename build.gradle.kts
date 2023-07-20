plugins {
    kotlin("multiplatform") version "1.9.0"
}

group = "net.codinux"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}


kotlin {
    // Enable the default target hierarchy:
    targetHierarchy.default()

    jvm {
        jvmToolchain(8)
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    js(IR) {
        moduleName = "kmp-base"
        binaries.executable()

        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                    useFirefoxHeadless()
                }
            }
        }

        nodejs {

        }
    }


    linuxX64()
    mingwX64()


    ios {
        binaries {
            framework {
                baseName = "kmp-base"
            }
        }
    }
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    watchos()
    watchosSimulatorArm64()
    tvos()
    tvosSimulatorArm64()


    sourceSets {
        val coroutinesVersion: String by project
        val kotestVersion: String by project

        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))

                implementation("io.kotest:kotest-assertions-core:$kotestVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }

        val jvmMain by getting
        val jvmTest by getting

        val jsMain by getting
        val jsTest by getting

        val nativeMain by getting
        val nativeTest by getting

        val linuxAndMingwMain by creating {
            dependsOn(nativeMain)
        }
        val linuxMain by getting {
            dependsOn(linuxAndMingwMain)
        }
        val mingwMain by getting {
            dependsOn(linuxAndMingwMain)
        }
    }
}


tasks.named<Test>("jvmTest") {
    useJUnitPlatform()

    maxParallelForks = 4
//    maxHeapSize = "1G"

    filter {
        isFailOnNoMatchingTests = false
    }
    testLogging {
        showExceptions = true
        showStandardStreams = true
        events = setOf(
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
        )
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}
