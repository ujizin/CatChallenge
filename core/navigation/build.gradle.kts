plugins {
    id("com.ujizin.android-compose")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ujizin.catchallenge.core.navigation"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(libs.androidx.compose.ui.navigation)
    implementation(libs.kotlinx.serialization.json)
}