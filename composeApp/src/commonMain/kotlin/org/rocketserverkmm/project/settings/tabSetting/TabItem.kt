package org.rocketserverkmm.project.settings.tabSetting

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.rocketserverkmm.project.presentation.screens.LaunchListScreen
import org.rocketserverkmm.project.presentation.screens.SettingsScreen

sealed class TabItem {
    object LaunchesTab : Tab {
        @Composable
        override fun Content() {
            Navigator(LaunchListScreen())
        }

        override val options: TabOptions
            @Composable
            get() = TabOptions(
                index = 0u,
                title = "Launches",
                icon = rememberVectorPainter(Icons.Default.Add)
            )
    }

    object SettingsTab : Tab {
        @Composable
        override fun Content() {
            Navigator(SettingsScreen())
        }

        override val options: TabOptions
            @Composable
            get() = TabOptions(
                index = 1u,
                title = "Settings",
                icon = rememberVectorPainter(Icons.Default.Settings)
            )
    }
}

@Composable
fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            Icon(
                painter = tab.options.icon ?: rememberVectorPainter(
                    Icons.Default.Add
                ),
                contentDescription = tab.options.title,
            )
        },
        label = { Text(tab.options.title) }
    )
}