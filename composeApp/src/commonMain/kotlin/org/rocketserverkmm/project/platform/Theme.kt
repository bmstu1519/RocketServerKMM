package org.rocketserverkmm.project.platform

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.map
import org.koin.compose.getKoin
import org.rocketserverkmm.project.data.local.UserConfigHolder
import org.rocketserverkmm.project.presentation.utils.theme.Pink40
import org.rocketserverkmm.project.presentation.utils.theme.Pink80
import org.rocketserverkmm.project.presentation.utils.theme.Purple40
import org.rocketserverkmm.project.presentation.utils.theme.Purple80
import org.rocketserverkmm.project.presentation.utils.theme.PurpleGrey40
import org.rocketserverkmm.project.presentation.utils.theme.PurpleGrey80

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

@Composable
internal fun RocketReserverKMMTheme(
    content: @Composable () -> Unit
) {
    val userConfigHolder: UserConfigHolder = getKoin().get()
    val isDarkThemeEnabled by userConfigHolder.state
        .map { it.isDarkThemeEnabled }
        .collectAsState(initial = false)

    val isDarkState = mutableStateOf(isDarkThemeEnabled)
    CompositionLocalProvider(
        LocalThemeIsDark provides isDarkState
    ) {
        val isDark by isDarkState
        SystemAppearance(!isDark)
        MaterialTheme(
            colorScheme = if (isDark) DarkColorScheme else LightColorScheme,
            content = { Surface(content = content) }
        )
    }
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
