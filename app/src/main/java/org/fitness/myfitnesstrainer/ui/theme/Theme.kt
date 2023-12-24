package org.fitness.myfitnesstrainer.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.core.view.WindowCompat
import com.google.android.material.color.utilities.MaterialDynamicColors.onBackground
import com.google.android.material.color.utilities.MaterialDynamicColors.onPrimary
import com.google.android.material.color.utilities.MaterialDynamicColors.onSecondary
import com.google.android.material.color.utilities.MaterialDynamicColors.onSurface
import com.google.android.material.color.utilities.MaterialDynamicColors.primaryInverse

private val DarkColorScheme = darkColorScheme(
    background = surfaceBlack,
    surface = surfaceBlack,
    onSurface = surfaceBlack,
    outline = surfaceWhite,
    primary = primaryDark,
    onPrimary = Color.White,
    inversePrimary = surfaceWhite,
    primaryContainer = primaryDarkContainer,
    secondary = primaryDarkGold,
    secondaryContainer = secondaryDarkContainer,
    onSecondary = Color.White,
    onSecondaryContainer = Color.White,
    tertiary = tertiaryDarkPurple,
    tertiaryContainer = tertiaryDarkPurple,
    scrim = scrimDark
)

private val LightColorScheme = lightColorScheme(
    primary = surfaceWhite,
    secondary = surfaceWhite,
    tertiary = surfaceWhite,
    background = surfaceWhite,
    surface = surfaceWhite,
    outline = Color.Magenta
)

@Composable
fun MyFitnessTrainerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val textColor = when (darkTheme) {
        true -> {
            Color.White

        }

        false -> {
            Color.Black
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = {
            ProvideTextStyle(
                value = TextStyle(color = textColor),
                content = content
            )
        }
    )
}