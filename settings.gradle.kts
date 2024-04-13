pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // Add JitPack repository here
        maven { url = uri("https://jitpack.io") } // Add JitPack repository

    }
}

rootProject.name = "communitycircuit1"
include(":app")
 