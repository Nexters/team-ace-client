plugins {
    `kotlin-dsl`

}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
    compileOnly(libs.compose.gradle.plugin)
}


gradlePlugin {
    plugins {
        register("kmpConvention") {
            id = "ace.kotlin.multiplatform"
            implementationClass =
                "com.nexters.ace.conventions.plugin.KotlinMultiPlatformConventionPlugin"
        }
        register("cmpConvention") {
            id = "ace.compose.multiplatform"
            implementationClass =
                "com.nexters.ace.conventions.plugin.ComposeMultiPlatformConventionPlugin"
        }
        register("aceFeature") {
            id = "ace.feature"
            implementationClass =
                "com.nexters.ace.conventions.plugin.AceFeaturePlugin"
        }
    }
}
