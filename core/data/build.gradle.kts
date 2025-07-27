plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ace.kotlin.multiplatform)
}

android.namespace = "com.nexters.ace.core.data"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)

            implementation(libs.koin.core)
        }


    }
}