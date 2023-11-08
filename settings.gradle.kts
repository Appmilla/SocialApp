pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(org.gradle.api.initialization.resolve.RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("https://jitpack.io") }
        google()
        mavenCentral()
    }
}

rootProject.name = "vsl_social"
include(":androidApp")
include(":shared")
//include(":amity-uikit")

//project(":amity-uikit").projectDir = File(settingsDir, "../Amity-Social-Cloud-UIKit-Android-OpenSource/amity-uikit")