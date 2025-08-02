package com.nexters.emotia.conventions.plugin

import com.nexters.emotia.conventions.applyComposePlugins
import com.nexters.emotia.conventions.primitive.composeMultiplatformDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class ComposeMultiPlatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        applyComposePlugins()
        composeMultiplatformDependencies()
    }
}
