plugins {
    id("com.ujizin.android-compose")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ujizin.catchallenge.core.ui"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}
