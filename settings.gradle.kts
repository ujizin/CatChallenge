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
    }
}

rootProject.name = "Cat Challenge"

include(":app")
include(":core:data")
include(":core:test")
include(":core:navigation")
include(":core:ui")
include(":core:model")

include(":features:home")
include(":features:favorite")
include(":features:breeddetail")
