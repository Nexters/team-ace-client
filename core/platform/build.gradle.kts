plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.emotia.kotlin.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
        }

        androidMain.dependencies {
            // Android specific dependencies if needed
        }

        iosMain.dependencies {
            // iOS specific dependencies if needed
        }
    }
}

android.namespace = "com.nexters.emotia.core.platform"