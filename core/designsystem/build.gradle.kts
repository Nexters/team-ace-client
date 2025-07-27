plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ace.kotlin.multiplatform)
    alias(libs.plugins.ace.compose.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.coil)
        }
    }
}

compose.resources {
    publicResClass = true
}

android.namespace = "com.nexters.ace.core.designsystem"

