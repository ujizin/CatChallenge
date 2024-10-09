plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleApi())
    implementation(libs.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.gradle.api.plugin)
    implementation(libs.hilt.plugin)
    implementation(libs.ksp.plugin)
    implementation(libs.compose.plugin)
    implementation(libs.kover.plugin)
}