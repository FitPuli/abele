plugins {
    id("kotlin-multiplatform")
    id("maven-publish")
    id("org.jetbrains.dokka") version "1.4.30"
    id("signing")
}

group = "hu.fitpuli"
version = "0.2.2"

kotlin {
    ios()

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

        val nativeMain by creating {
            dependsOn(commonMain)
        }
        val nativeTest by creating {
            dependsOn(commonTest)
        }

        val iosMain by getting {
            dependsOn(nativeMain)
        }
        val iosTest by getting {
            dependsOn(nativeTest)
        }
    }
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn(tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(tasks.dokkaHtml.get().outputDirectory)
}

tasks {
    dokkaHtml {
        dokkaSourceSets {
            all { isEnabled = false }
            named("commonMain") {
                isEnabled = true
                displayName.set("common")
                platform.set(org.jetbrains.dokka.Platform.common)
            }
        }
    }
}

// Stub secrets to let the project sync and build without the publication values set up
ext["signing.keyId"] = null
ext["signing.password"] = null
ext["signing.secretKeyRingFile"] = null
ext["ossrhUsername"] = null
ext["ossrhPassword"] = null

// Grabbing secrets from local.properties file or from environment variables, which could be used on CI

ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")

fun getExtraString(name: String) = ext[name]?.toString()

publishing {
    // Configure maven central repository
    repositories {
        maven {
            name = "sonatype"
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = getExtraString("ossrhUsername")
                password = getExtraString("ossrhPassword")
            }
        }
    }

    // Configure all publications
    publications.withType<MavenPublication> {

        // Stub javadoc.jar artifact
        artifact(javadocJar.get())

        // Provide artifacts information requited by Maven Central
        pom {
            name.set("Abele")
            description.set("Abele is a Kotlin Mutliplatform Logging library with a lightweight API")
            url.set("https://github.com/FitPuli/abele")

            licenses {
                license {
                    name.set("Apache License 2.0")
                    url.set("https://github.com/FitPuli/abele/blob/master/LICENSE")
                }
            }
            developers {
                developer {
                    id.set(System.getenv("DEVELOPER_ID"))
                    name.set(System.getenv("DEVELOPER_NAME"))
                    email.set(System.getenv("DEVELOPER_EMAIL"))
                }
            }
            scm {
                url.set("https://github.com/FitPuli/abele.git")
            }
        }
    }
}

signing {
    sign(publishing.publications)
}
