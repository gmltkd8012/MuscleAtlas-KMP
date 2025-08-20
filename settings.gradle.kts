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
include(":app")
include(":core:data")
include(":core:design-system")
include(":core:domain")
include(":core:room")
include(":core:util")
include(":core:ui")
include(":feature:main")
include(":feature:client")
include(":feature:setting")
