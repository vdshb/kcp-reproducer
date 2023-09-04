package com.example.gp

import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

@Suppress("unused")
class AllowTestReturnGradlePlugin : KotlinCompilerPluginSupportPlugin {

    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): org.gradle.api.provider.Provider<List<SubpluginOption>> {
        val project = kotlinCompilation.target.project
        return project.provider { listOf() }
    }

    override fun getCompilerPluginId() = "com.example.kcp.kcpPluginId"

    override fun getPluginArtifact(): SubpluginArtifact {
        return SubpluginArtifact(
            groupId = "com.example.kcp",
            artifactId = "compiler-plugin",
            version = "1"
        )
    }

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true
}