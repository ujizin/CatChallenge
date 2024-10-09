import com.ujizin.catchallenge.utils.configAndroid

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("com.ujizin.test-coverage")
}

internal val Project.libs: VersionCatalog
    get() = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

android {
    configAndroid(project)
    kotlinOptions {
        jvmTarget = "17"
    }
    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.findLibrary("hilt").get())
    ksp(libs.findLibrary("hilt-compiler").get())

    testImplementation(libs.findBundle("unit-test").get())
}
