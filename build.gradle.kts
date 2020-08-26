import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import org.jetbrains.kotlin.konan.target.HostManager

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven { setUrl("https://kotlin.bintray.com/kotlinx") }
    }

    val kotlinVersion: String by project

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { setUrl("https://kotlin.bintray.com/kotlinx") }
    }
}

subprojects {
    apply<DetektPlugin>()

    detekt {
        failFast = true // fail build on any finding
        buildUponDefaultConfig = true // preconfigure defaults
        parallel = true
        autoCorrect = true
        config = files("$rootDir/config/detekt/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior

        reports {
            html.enabled = true // observe findings in your browser with structure and code snippets
            xml.enabled = true // checkstyle like format mainly for integrations like Jenkins
            txt.enabled = true // similar to the console output, contains issue signature to manually edit baseline files
        }

        input.from(
            files(projectDir.resolve("src/commonMain/kotlin")),
            files(projectDir.resolve("src/jvmMain/kotlin")),
            files(projectDir.resolve("src/iosMain/kotlin"))
        )
    }

    tasks {
        withType<Detekt> {
            // Target version of the generated JVM bytecode. It is used for type resolution.
            this.jvmTarget = "1.8"
        }
    }

    dependencies {
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.12.0")
    }
}

plugins {
    id("kotlinx.team.infra") version "0.2.0-dev-55"
    id("io.gitlab.arturbosch.detekt") version "1.12.0"
}

infra {
    publishing {
        include(":abele")

//        bintray {
//            organization = "kotlin"
//            repository = "kotlinx"
//            library = "kotlinx.datetime"
//            username = findProperty("bintrayUser") as String?
//            password = findProperty("bintrayApiKey") as String?
//        }

        bintrayDev {
            publish = System.getProperty("idea.active") != "true"
            organization = "fitpuli"
            repository = "fitpuli.dev"
            library = "abele"
            username = (project.findProperty("bintray.user") as? String) ?: ""
            password = (project.findProperty("bintray.pass") as? String) ?: ""
        }
    }
}

val clean by tasks.creating(Delete::class) {
    delete(rootProject.buildDir)
}