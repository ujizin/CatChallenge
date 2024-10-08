package com.ujizin.catchallenge.utils

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

fun BaseAppModuleExtension.configApplication(rootProject: Project) = with(rootProject) {
    defaultConfig {
        applicationId = "com.ujizin.catchallenge"
        versionCode = 1
        versionName = "1.0"
    }
    configAndroid(project)
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
