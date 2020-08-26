pluginManagement {
    repositories {
        google()
        jcenter()
        maven { setUrl("https://dl.bintray.com/orangy/maven") }
        maven { setUrl("https://dl.bintray.com/kotlin/kotlinx") }
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
        gradlePluginPortal()
    }
}

enableFeaturePreview("GRADLE_METADATA")

rootProject.name = "abele-library"

include(":abele")
