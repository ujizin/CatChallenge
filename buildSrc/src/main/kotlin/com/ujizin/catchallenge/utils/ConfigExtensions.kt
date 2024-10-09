package com.ujizin.catchallenge.utils

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.tasks.StopExecutionException
import java.util.Properties

fun BaseAppModuleExtension.configApplication(rootProject: Project) = with(rootProject) {
    defaultConfig {
        applicationId = "com.ujizin.catchallenge"
        versionCode = 2
        versionName = "0.2.0"
    }
    configAndroid(project)
}

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

fun CommonExtension<*, *, *, *, *, *>.configAndroid(project: Project) {
    compileSdk = 34
    defaultConfig {
        minSdk = 24
        buildConfigField("String", "CDN_URL", "\"https://cdn2.thecatapi.com/images\"")
        buildConfigField("String", "BASE_URL", "\"https://api.thecatapi.com\"")
        configApiKey(project)
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    testOptions {
        unitTests.all {
            it.jvmArgs("-noverify")
        }
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
}
