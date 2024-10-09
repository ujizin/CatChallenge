plugins {
    id("com.ujizin.android-application")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ujizin.catchallenge"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))

    implementation(project(":features:home"))
    implementation(project(":features:favorite"))
    implementation(project(":features:breeddetail"))

    kover(project(":features:home"))
    kover(project(":features:favorite"))
    kover(project(":features:breeddetail"))
    kover(project(":core:data"))
}

apply(from = "$rootDir/coverage.gradle.kts")