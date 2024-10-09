plugins {
    id("org.jetbrains.kotlinx.kover")
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
            "hilt_aggregated_deps",
            "*_Impl",
            "*_Impl\$*",
            "**.*Module*.*",
            "**.BuildConfig.*",
            "**.ComposableSingletons*.*",
            "**.ComposableSingletons\$*",
            "**.ui.preview.**",
            "**.ui.themes.**",
            "**.ui.utils.**",
            "**.navigation",
            "**.navigation.**",
            "**.ui.**",
        )
        annotatedBy(
            "androidx.compose.ui.tooling.preview.Preview",
            "kotlinx.serialization.Serializable",
            "com.ujizin.catchallenge.core.data.utils.IgnoreKover"
        )
    }
}