plugins {
    id("com.ujizin.android-library")
}

android {
    namespace = "com.ujizin.catchallenge.core.test"
}

dependencies {
    implementation(libs.coroutines.test)
    implementation(libs.junit)
}