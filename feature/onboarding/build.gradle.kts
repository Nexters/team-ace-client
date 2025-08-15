plugins {
    alias(libs.plugins.emotia.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain.onboarding)
            implementation(libs.slf4j.api)
            implementation(libs.slf4j.android)
        }
    }
}

android.namespace = "com.nexters.emotia.feature.onboarding"
