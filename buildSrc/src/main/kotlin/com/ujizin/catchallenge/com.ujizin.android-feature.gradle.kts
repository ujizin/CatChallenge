plugins {
    id("com.ujizin.android-compose")
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
    testImplementation(project(":core:test"))
    androidTestImplementation(project(":core:test"))
}
