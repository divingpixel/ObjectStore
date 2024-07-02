package com.epikron.objectstore.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.epikron.countriesandflags.ui.theme.Shapes

private val darkThemeColors = darkColorScheme(
    primary = DarkThemeColors.primary,
    onPrimary = DarkThemeColors.onPrimary,
    primaryContainer = DarkThemeColors.primaryContainer,
    onPrimaryContainer = DarkThemeColors.onPrimaryContainer,
    secondary = DarkThemeColors.secondary,
    onSecondary = DarkThemeColors.onSecondary,
    secondaryContainer = DarkThemeColors.secondaryContainer,
    onSecondaryContainer = DarkThemeColors.onSecondaryContainer,
    background = DarkThemeColors.background,
    onBackground = DarkThemeColors.onBackground,
    surface = DarkThemeColors.surface,
    onSurface = DarkThemeColors.onSurface,
)

private val lightThemeColors = lightColorScheme(
    primary = LightThemeColors.primary,
    onPrimary = LightThemeColors.onPrimary,
    primaryContainer = LightThemeColors.primaryContainer,
    onPrimaryContainer = LightThemeColors.onPrimaryContainer,
    secondary = LightThemeColors.secondary,
    onSecondary = LightThemeColors.onSecondary,
    secondaryContainer = LightThemeColors.secondaryContainer,
    onSecondaryContainer = LightThemeColors.onSecondaryContainer,
    surface = LightThemeColors.surface,
    onSurface = LightThemeColors.onSurface,
    background = LightThemeColors.background,
    onBackground = LightThemeColors.onBackground
)


@Composable
fun ObjectStoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        darkTheme || (Build.VERSION.SDK_INT < 29) -> darkThemeColors
        else -> lightThemeColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = mainTypography,
        content = content,
        shapes = Shapes
    )
}