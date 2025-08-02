plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.emotia.kotlin.multiplatform)
    alias(libs.plugins.emotia.compose.multiplatform)
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

android.namespace = "com.nexters.emotia.core.designsystem"

