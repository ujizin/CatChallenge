package com.ujizin.catchallenge.utils

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.tasks.StopExecutionException
import java.util.Properties

fun CommonExtension<*, *, *, *, *, *>.configApiKey(project: Project) {
    val localProperties = Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")

    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
    }

    val apiKey: String = localProperties["API_KEY"]?.toString()
        ?: System.getenv("API_KEY")
        ?: throw StopExecutionException(
            """
                You must specify a valid API_KEY in the local.properties file to proceed.
                To obtain your API_KEY, please visit The Cat API at https://thecatapi.com 
            """.trimIndent()
        )

    defaultConfig {
        buildConfigField("String", "API_KEY", apiKey)
    }
}