package org.fitness.myfitnesstrainer.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository
import timber.log.Timber

val DarkColorScheme = darkColorScheme(
    background = surfaceBlack,
    surface = surfaceBlack,
    onSurface = surfaceBlack,
    outline = Color.White,
    primary = primaryDark,
    onPrimary = Color.White,
    inversePrimary = Color.White,
    primaryContainer = primaryDarkContainer,
    secondary = primaryDarkGold,
    secondaryContainer = secondaryDarkContainer,
    onSecondary = Color.White,
    onSecondaryContainer = Color.White,
    tertiary = tertiaryDarkPurple,
    tertiaryContainer = tertiaryDarkPurple,
    scrim = scrimDark
)

val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    secondary = primaryLight,
    tertiary = Color.Black,
    background = surfaceLight,
    surface = surfaceLight,
    outline = Color.Black,
    onSurface = surfaceBlack,
    onPrimary = Color.White,
    inversePrimary = Color.Black,
    primaryContainer = Color.White,
    secondaryContainer = Color.White,
    onSecondary = Color.White,
    onSecondaryContainer = Color.White,
    tertiaryContainer = lightTertiaryContainer,
    scrim = scrimDark
)

@Composable
fun MyFitnessTrainerTheme(content: @Composable () -> Unit) {
    val theme = MyFitnessRepository.theme.observeAsState()

    Timber.i("Theme: ${theme.value}")

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            if (theme.value != null) {
                window.statusBarColor = theme.value!!.primary.toArgb()
            } else {
                window.statusBarColor = Color.Black.toArgb()
            }
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                (theme.value == DarkColorScheme)
        }
    }

    val textColor = when (theme.value == DarkColorScheme) {
        true -> {
            Color.White

        }

        false -> {
            Color.Black
        }
    }

    MaterialTheme(
        colorScheme = if (theme.value != null) {
            theme.value!!
        } else {
            DarkColorScheme
        },
        typography = Typography,
        content = {
            ProvideTextStyle(
                value = TextStyle(color = textColor),
                content = content
            )
        }
    )
}