pluginManagement {
    includeBuild("build-logic")

    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MuscleAtlas"

// App entry points
include(":composeApp")
include(":androidApp")

// Core modules (KMP)
include(":core:data")
include(":core:domain")
include(":core:model")
include(":core:database")
include(":core:datastore")
include(":core:network")

// Shared UI (CMP)
include(":core:ui")
include(":core:design-system")
