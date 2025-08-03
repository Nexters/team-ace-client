plugins {
    alias(libs.plugins.emotia.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
        }
    }
}

android.namespace = "com.nexters.emotia.feature.result"
