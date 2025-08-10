rootProject.name = "emotia"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")

include(":core:designsystem")
include(":core:model:fairybook")

include(":core:data:chatting")
include(":core:data:onboarding")

include(":core:domain:chatting")

include(":core:network")
include(":core:navigation")

include(":feature:main")
include(":feature:onboarding")
include(":feature:chatting")
include(":feature:result")
