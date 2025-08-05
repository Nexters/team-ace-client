plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.emotia.kotlin.multiplatform)
    alias(libs.plugins.emotia.compose.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.coil)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.foundation)
        }
        androidMain.dependencies {
            implementation(compose.uiTooling)
        }
    }
}

compose.resources {
    publicResClass = true
}

android.namespace = "com.nexters.emotia.core.designsystem"

