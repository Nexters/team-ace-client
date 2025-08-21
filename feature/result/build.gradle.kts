plugins {
    alias(libs.plugins.emotia.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.coil)
            implementation(libs.slf4j.api)
            implementation(libs.slf4j.android)
            implementation(projects.core.domain.letter)
        }
    }
}

android.namespace = "com.nexters.emotia.feature.result"
