plugins {
    alias(libs.plugins.emotia.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain.chat)
            implementation(projects.core.designsystem)
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.slf4j.api)
            implementation(libs.slf4j.android)
        }
    }
}

android.namespace = "com.nexters.emotia.feature.chatting"
