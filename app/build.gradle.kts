plugins {
    id("com.ujizin.android-application")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kover)
}

android {
    namespace = "com.ujizin.catchallenge"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))

    implementation(libs.androidx.compose.paging)
    implementation(libs.kotlinx.serialization.json)

//    // UI Test
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(libs.androidx.ui.test.junit4)
}

kover {
    reports.filters.excludes {
        androidGeneratedClasses()
        classes(
            "*_MembersInjector",
            "*Module_*Factory",
            "*Factory*.*",
            "**.di.**",
            "*_Impl",
            "dagger.hilt.*",
            "hilt_aggregated_deps.*",
            "*_Factory",
            "*_Factory\$*",
            "*_*Factory",
            "*_*Factory\$*",
            "*.Hilt_*",
            "*_HiltModules",
            "*_HiltModules\$*",
            "*_Impl",
            "*_Impl\$*",
            "**.*Module*.*",
            "**.BuildConfig.*",
            "**.ComposableSingletons*.*",
            "**.ComposableSingletons\$*",
            "**.ui.preview.**",
            "**.ui.themes.**",
            "**.ui.utils.**",
            "**.core.navigation.**",
            "**.ui.**",
        )
        annotatedBy(
            "androidx.compose.ui.tooling.preview.Preview",
            "kotlinx.serialization.Serializable",
            "com.ujizin.catchallenge.core.data.utils.IgnoreKover"
        )
    }
}

apply(from = "$rootDir/coverage.gradle.kts")