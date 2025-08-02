package com.nexters.emotia.conventions.plugin

import com.nexters.emotia.conventions.applyAndroidLibraryPlugin
import com.nexters.emotia.conventions.applyComposePlugins
import com.nexters.emotia.conventions.applyKmpPlugins
import com.nexters.emotia.conventions.applyKmpPrimitives
import com.nexters.emotia.conventions.library
import com.nexters.emotia.conventions.libs
import com.nexters.emotia.conventions.primitive.composeMultiplatformDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class EmotiaFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        applyAndroidLibraryPlugin()
        applyKmpPlugins()
        applyComposePlugins()
        applyKmpPrimitives()
        composeMultiplatformDependencies()

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.apply {
                commonMain {
                    dependencies {
                        implementation(project(":core:designsystem"))
                        implementation(project(":core:navigation"))
                        implementation(libs.library("androidx-lifecycle-runtimeCompose"))
                        implementation(libs.library("androidx-navigation3-runtime"))
                        implementation(libs.library("androidx-navigation3-ui"))
                        implementation(libs.library("androidx-lifecycle-viewmodel-navigation3"))
                        implementation(libs.library("kotlinx-immutable"))
                    }
                }
            }
        }
    }
}
