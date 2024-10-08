plugins {
    id("com.ujizin.android-library")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ujizin.core.data"
}

dependencies {
    // Paging
    implementation(libs.androidx.paging.runtime)

    // Network
    implementation(libs.retrofit)
    implementation(libs.ok.http)
    implementation(libs.retrofit.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization.json)

    // Unit Test
    testImplementation(project(":core:test"))
    testImplementation(libs.androidx.paging.testing.android)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.core.test.ktx)

    // Local
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)
}
