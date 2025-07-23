package com.nexters.ace.conventions.plugin

import com.nexters.ace.conventions.applyComposePlugins
import com.nexters.ace.conventions.primitive.composeMultiplatformDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class ComposeMultiPlatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        applyComposePlugins()
        composeMultiplatformDependencies()
    }
}
