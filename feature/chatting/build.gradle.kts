plugins {
    alias(libs.plugins.emotia.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain.chatting)
            implementation(projects.core.platform)
            implementation(libs.slf4j.api)
            implementation(libs.slf4j.android)
            implementation(libs.kotlinx.datetime)
        }
    }
}


android.namespace = "com.nexters.emotia.feature.chatting"
