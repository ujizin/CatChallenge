import com.ujizin.catchallenge.utils.configApplication

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("com.ujizin.test-coverage")
}

internal val Project.libs: VersionCatalog
    get() = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

android {
    configApplication(project)
    kotlinOptions {
        jvmTarget = "17"
    }
    hilt {
        enableAggregatingTask = true
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.findLibrary("androidx-compose-bom").get()))
    implementation(libs.findBundle("androidx-compose").get())
    implementation(libs.findLibrary("androidx-material3").get())
    implementation(libs.findLibrary("coil").get())

    implementation(libs.findLibrary("hilt").get())
    ksp(libs.findLibrary("hilt-compiler").get())
}
