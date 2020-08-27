import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin

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
        failFast = true
        buildUponDefaultConfig = true
        parallel = true
        autoCorrect = true
        config = rootProject.files("config/detekt/detekt.yml")

        reports {
            html.enabled = true
            xml.enabled = true
            txt.enabled = true
        }

        input.from(
            files(projectDir.resolve("src/commonMain/kotlin")),
            files(projectDir.resolve("src/commonTest/kotlin")),
            files(projectDir.resolve("src/jvmMain/kotlin")),
            files(projectDir.resolve("src/jvmTest/kotlin")),
            files(projectDir.resolve("src/nativeMain/kotlin")),
            files(projectDir.resolve("src/nativeTest/kotlin"))
        )
    }

    tasks {
        withType<Detekt> { jvmTarget = "1.8" }
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