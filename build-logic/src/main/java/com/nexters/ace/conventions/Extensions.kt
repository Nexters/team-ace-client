package com.nexters.ace.conventions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.nexters.ace.conventions.primitive.KotlinMultiPlatformAndroidPlugin
import com.nexters.ace.conventions.primitive.KotlinMultiPlatformIOSPlugin
import com.nexters.ace.conventions.primitive.KotlinMultiPlatformPlugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependency

internal val ExtensionContainer.libs: VersionCatalog
    get() = getByType<VersionCatalogsExtension>().named("libs")

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val Project.applicationExtension: CommonExtension<*, *, *, *, *, *>
    get() = extensions.getByType<ApplicationExtension>()

internal val Project.libraryExtension: CommonExtension<*, *, *, *, *, *>
    get() = extensions.getByType<LibraryExtension>()

internal val Project.androidExtension: CommonExtension<*, *, *, *, *, *>
    get() = runCatching { libraryExtension }
        .recoverCatching { applicationExtension }
        .onFailure { println("Could not find Library or Application extension from this project") }
        .getOrThrow()

internal fun VersionCatalog.version(name: String): String {
    return findVersion(name).get().requiredVersion
}

internal fun VersionCatalog.library(name: String): MinimalExternalModuleDependency {
    return findLibrary(name).get().get()
}

internal fun VersionCatalog.plugin(name: String): PluginDependency {
    return findPlugin(name).get().get()
}

internal fun VersionCatalog.bundle(name: String): ExternalModuleDependencyBundle {
    return findBundle(name).get().get()
}

/**
 * Kotlin Multiplatform의 기본 primitive 플러그인들을 일괄 적용하는 확장함수
 */
internal fun Project.applyKmpPrimitives() {
    apply<KotlinMultiPlatformPlugin>()
    apply<KotlinMultiPlatformAndroidPlugin>()
    apply<KotlinMultiPlatformIOSPlugin>()
}

/**
 * Kotlin Multiplatform 관련 플러그인들을 일괄 적용하는 확장함수
 */
internal fun Project.applyKmpPlugins() {
    with(pluginManager) {
        apply(libs.plugin("kotlinMultiplatform").pluginId)
    }
}

/**
 * Compose Multiplatform 관련 플러그인들을 일괄 적용하는 확장함수
 */
internal fun Project.applyComposePlugins() {
    with(pluginManager) {
        apply(libs.plugin("composeMultiplatform").pluginId)
        apply(libs.plugin("composeCompiler").pluginId)
    }
}

/**
 * Android Library 플러그인을 적용하는 확장함수
 */
internal fun Project.applyAndroidLibraryPlugin() {
    with(pluginManager) {
        apply(libs.plugin("androidLibrary").pluginId)
    }
}
