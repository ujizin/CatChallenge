plugins {
    id("com.ujizin.android-library")
}

android {
    namespace = "com.ujizin.catchallenge.core.test"
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.coroutines.test)
    implementation(libs.junit)
}