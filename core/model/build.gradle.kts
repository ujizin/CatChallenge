import com.ujizin.catchallenge.utils.configAndroid

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ujizin.catchallenge.core.model"
    configAndroid(project)
    kotlinOptions {
        jvmTarget = "17"
    }
}
