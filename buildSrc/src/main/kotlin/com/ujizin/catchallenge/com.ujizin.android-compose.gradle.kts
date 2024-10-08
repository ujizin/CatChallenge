plugins {
    id("com.ujizin.android-library")
    id("org.jetbrains.kotlin.plugin.compose")
}

internal val Project.libs: VersionCatalog
    get() = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

android {
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.findLibrary("androidx-lifecycle-runtime-ktx").get())
    implementation(libs.findLibrary("androidx-activity-compose").get())
    implementation(libs.findLibrary("androidx-paging-runtime").get())
    implementation(platform(libs.findLibrary("androidx-compose-bom").get()))
    implementation(libs.findBundle("androidx-compose").get())
    implementation(libs.findLibrary("androidx-material3").get())
    implementation(libs.findLibrary("coil").get())
    implementation(libs.findLibrary("androidx-core-test-ktx").get())
    debugImplementation(libs.findBundle("androidx-compose-debug").get())
}
