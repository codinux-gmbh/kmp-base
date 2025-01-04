@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl


plugins {
    kotlin("multiplatform")
    id("com.android.library")
}


kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        // suppresses compiler warning: [EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING] 'expect'/'actual' classes (including interfaces, objects, annotations, enums, and 'actual' typealiases) are in Beta.
        freeCompilerArgs.add("-Xexpect-actual-classes")

        // avoid "variable has been optimized out" in debugging mode
        if (System.getProperty("idea.debugger.dispatch.addr") != null) {
            freeCompilerArgs.add("-Xdebug")
        }
    }


    jvmToolchain(11)

    jvm()

    androidTarget {
//        @OptIn(ExperimentalKotlinGradlePluginApi::class)
//        compilerOptions {
//            jvmTarget.set(JvmTarget.JVM_11)
//        }

        publishLibraryVariants("release")
    }

    js {
        moduleName = "kmp-base"
        binaries.executable()

        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    useFirefoxHeadless()
                }
            }
        }

        nodejs {
            testTask {
                useMocha {
                    timeout = "20s" // Mocha times out after 2 s, which is too short for some tests
                }
            }
        }
    }

    wasmJs {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    useFirefoxHeadless()
                }
            }
        }
    }


    linuxX64()
    mingwX64()

    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()

    watchosArm64()
    watchosSimulatorArm64()
    tvosArm64()
    tvosSimulatorArm64()

    applyDefaultHierarchyTemplate()


    val assertKVersion: String by project

    sourceSets {
        commonMain.dependencies {

        }
        commonTest.dependencies {
            implementation(kotlin("test"))

            implementation("com.willowtreeapps.assertk:assertk:$assertKVersion")
        }


        val javaCommonMain by creating {
            dependsOn(commonMain.get())
        }

        val javaCommonTest by creating {
            dependsOn(commonTest.get())
        }

        val jvmMain by getting {
            dependsOn(javaCommonMain)
        }
        val jvmTest by getting {
            dependsOn(javaCommonTest)
        }

        val androidMain by getting {
            dependsOn(javaCommonMain)
        }
        val androidUnitTest by getting {
            dependsOn(javaCommonTest)
        }


//        val nonJvmMain by creating {
//            dependsOn(commonMain.get())
//        }
//        nativeMain {
//            dependsOn(nonJvmMain)
//        }
    }
}


android {
    namespace = "net.codinux.kotlin.kmp_base"

    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    lint {
        this.abortOnError = false
        this.checkReleaseBuilds = false
    }
}



apply(from = "../gradle/scripts/publish-codinux.gradle.kts")