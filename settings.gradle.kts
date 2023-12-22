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
        gradlePluginPortal()
    }
}

rootProject.name = "ModularizedApp"
include(":app")
include(":domain")
include(":core")
include(":domain:models")
include(":data")

include(":test")
include(":abstraction")
include(":feature")
include(":feature:bookmark")
include(":navigation")
include(":feature:onBoarding")
