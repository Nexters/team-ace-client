plugins {
    alias(libs.plugins.emotia.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain.onboarding)
            implementation(projects.core.designsystem)
            implementation(libs.slf4j.api)
            implementation(libs.slf4j.android)
            implementation(projects.core.data.onboarding)
            implementation("androidx.security:security-crypto:1.1.0-alpha06")


        }
    }
}

android.namespace = "com.nexters.emotia.feature.onboarding"
