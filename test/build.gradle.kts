plugins {
    kotlin("multiplatform") version "1.9.10"
    id("com.example.gp.pluginId") version "1"
}

group = "com.example.kcptest"
version = "1"

kotlin {
    @Suppress("OPT_IN_USAGE")
    targetHierarchy.default {
        common {
            group("multithread") {
                withNative()
                withJvm()
            }
        }
    }
    jvm {
        jvmToolchain(11)
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    mingwX64()
    val hostOs = System.getProperty("os.name")
    if (hostOs == "Linux" || hostOs == "Mac OS X") {
        linuxX64()
    }
    if (hostOs == "Mac OS X") {
        macosX64()
//        macosArm64()
    }

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }
}
