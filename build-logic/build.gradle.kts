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
            id = "emotia.kotlin.multiplatform"
            implementationClass =
                "com.nexters.emotia.conventions.plugin.KotlinMultiPlatformConventionPlugin"
        }
        register("cmpConvention") {
            id = "emotia.compose.multiplatform"
            implementationClass =
                "com.nexters.emotia.conventions.plugin.ComposeMultiPlatformConventionPlugin"
        }
        register("emotiaFeature") {
            id = "emotia.feature"
            implementationClass =
                "com.nexters.emotia.conventions.plugin.EmotiaFeaturePlugin"
        }
    }
}
