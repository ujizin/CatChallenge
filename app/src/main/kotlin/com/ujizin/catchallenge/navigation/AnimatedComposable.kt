package com.ujizin.catchallenge.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.ujizin.catchallenge.core.ui.composition.LocalNavAnimatedVisibilityScope
import kotlin.reflect.KType

internal inline fun <reified T : Any> NavGraphBuilder.animatedComposable(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(typeMap = typeMap) {
        CompositionLocalProvider(
            LocalNavAnimatedVisibilityScope provides this,
        ) {
            content(it)
        }
    }
}