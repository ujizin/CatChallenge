package com.ujizin.catchallenge.core.ui.theme

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.ujizin.catchallenge.core.ui.composition.LocalNavAnimatedVisibilityScope
import com.ujizin.catchallenge.core.ui.composition.LocalSharedTransitionScope

private val DarkColorScheme = darkColorScheme(
    primary = Red40,
    secondary = Red40,
    onBackground = Gray,
    secondaryContainer = Red80,
)

private val LightColorScheme = lightColorScheme(
    primary = Red40,
    secondary = Red40,
    background = Gray,
    onBackground = Black,
    surfaceContainerHighest = Red10,
    surfaceContainerLow = White,
    surface = White,
    surfaceContainer = White,
    secondaryContainer = Red20,
)

@Composable
fun CatChallengeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun CatChallengeThemeForPreview(content: @Composable AnimatedContentScope.() -> Unit) {
    SharedTransitionLayout {
        AnimatedContent(MutableTransitionState(true)) { _ ->
            CompositionLocalProvider(
                LocalSharedTransitionScope provides this@SharedTransitionLayout,
                LocalNavAnimatedVisibilityScope provides this,
            ) {
                CatChallengeTheme {
                    content()
                }
            }
        }
    }
}
