package com.tasks.main.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val Teal = Color(0xFF03DAC5)

@Composable
fun themeColor(
    darkTheme: Boolean,
    dynamicColor: Boolean
) = when {
    dynamicColor && isSdkVersionPlus31() -> dynamicColor(darkTheme)
    darkTheme -> darkColorScheme
    else -> lightColorScheme
}

private fun isSdkVersionPlus31() =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@RequiresApi(Build.VERSION_CODES.S)
@Composable
private fun dynamicColor(darkTheme: Boolean) =
    if (darkTheme) dynamicDarkColorScheme(LocalContext.current)
    else dynamicLightColorScheme(LocalContext.current)

private val darkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    onPrimary = Purple40,
    onSecondary = PurpleGrey40,
    onTertiary = Pink40,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

private val lightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)