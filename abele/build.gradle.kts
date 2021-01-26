plugins {
    id("kotlin-multiplatform")
    id("maven-publish")
    id("org.jetbrains.dokka") version "1.4.20"
}

group = "hu.fitpuli"
version = "0.2.0"

kotlin {
    infra {
        target("linuxX64")
        target("mingwX64")
        target("macosX64")
        target("iosX64")
        target("iosArm64")
        target("iosArm32")
        target("watchosArm32")
        target("watchosArm64")
        target("watchosX86")
        target("tvosArm64")
        target("tvosX64")
    }

    jvm {
        attributes {
            attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 8)
        }
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }

        val nativeMain by getting {
            dependsOn(commonMain)
        }
        val nativeTest by getting {
            dependsOn(commonTest)
        }
    }
}
