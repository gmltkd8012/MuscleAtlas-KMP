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
include(":shared")
include(":androidApp")

// Core modules (KMP)
include(":core:data")
include(":core:domain")
include(":core:model")
include(":core:database")
include(":core:datastore")
include(":core:supabase")

// Shared UI (CMP)
include(":core:ui")
include(":core:design-system")
include(":core:app-config")
include(":feature:splash")
include(":feature:login")
include(":feature:member")
include(":feature:setting")
include(":feature:workout")
