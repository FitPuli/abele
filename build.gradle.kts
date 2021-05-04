import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    val kotlinVersion: String by project

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
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
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.14.2")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt") version "1.14.2"
    id("io.codearte.nexus-staging") version "0.30.0"
}

val clean by tasks.creating(Delete::class) {
    delete(rootProject.buildDir)
}

nexusStaging {
    username = System.getenv("OSSRH_USERNAME")
    password = System.getenv("OSSRH_PASSWORD")
    serverUrl = "https://s01.oss.sonatype.org/service/local/"
    packageGroup = "hu.fitpuli"
}
