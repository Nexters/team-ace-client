package com.nexters.ace.conventions.plugin

import com.nexters.ace.conventions.applyAndroidLibraryPlugin
import com.nexters.ace.conventions.applyComposePlugins
import com.nexters.ace.conventions.applyKmpPlugins
import com.nexters.ace.conventions.applyKmpPrimitives
import com.nexters.ace.conventions.library
import com.nexters.ace.conventions.libs
import com.nexters.ace.conventions.primitive.composeMultiplatformDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AceFeaturePlugin : Plugin<Project> {
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
                        // TODO : 공통적으로 필요한 core 모듈 의존성 추가 예정
                        implementation(libs.library("androidx-lifecycle-runtimeCompose"))
                    }
                }
            }
        }
    }
}
