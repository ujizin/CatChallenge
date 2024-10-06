import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kover)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")

if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

android {
    namespace = "com.ujizin.catchallenge"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ujizin.catchallenge"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "CDN_URL", "\"https://cdn2.thecatapi.com/images\"")
        buildConfigField("String", "BASE_URL", "\"https://api.thecatapi.com\"")
        buildConfigField("String", "API_KEY", "${localProperties["API_KEY"]}")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    // UI
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.compose.paging)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidx.compose)
    implementation(libs.androidx.material3)
    implementation(libs.coil)
    debugImplementation(libs.bundles.androidx.compose.debug)

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    // Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization.converter)
    implementation(libs.ok.http)

    // Local
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    // Unit Test
    testImplementation(libs.bundles.unit.test)
    testImplementation(libs.androidx.paging.testing.android)

    // UI Test
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
}

kover {
    reports.filters.excludes {
        androidGeneratedClasses()
        classes(
            "*_MembersInjector",
            "*Module_*Factory",
            "*Factory*.*",
            "**.di.**",
            "*_Impl",
            "dagger.hilt.*",
            "hilt_aggregated_deps.*",
            "*_Factory",
            "*_Factory\$*",
            "*_*Factory",
            "*_*Factory\$*",
            "*.Hilt_*",
            "*_HiltModules",
            "*_HiltModules\$*",
            "*_Impl",
            "*_Impl\$*",
            "**.*Module*.*",
            "**.BuildConfig.*",
            "**.ComposableSingletons*.*",
            "**.ComposableSingletons\$*",
            "**.ui.preview.**",
            "**.ui.themes.**",
            "**.ui.utils.**",
            "**.core.navigation.**",
            "**.ui.**",
        )
        annotatedBy("androidx.compose.ui.tooling.preview.Preview")
    }
}