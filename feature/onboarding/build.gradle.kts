plugins {
    alias(libs.plugins.emotia.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain.onboarding)
        }
    }
}

android.namespace = "com.nexters.emotia.feature.onboarding"
