plugins {
    id("com.ujizin.android-feature")
}

android {
    namespace = "com.ujizin.catchallenge.features.home"
}

dependencies {
    implementation(libs.androidx.compose.paging)
    testImplementation(libs.androidx.paging.testing.android)
}
