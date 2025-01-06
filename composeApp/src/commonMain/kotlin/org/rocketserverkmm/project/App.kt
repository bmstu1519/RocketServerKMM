package org.rocketserverkmm.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import coil3.compose.setSingletonImageLoaderFactory
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.rocketserverkmm.project.di.LocalScopeManager
import org.rocketserverkmm.project.di.ScopeManager
import org.rocketserverkmm.project.platform.RocketReserverKMMTheme
import org.rocketserverkmm.project.presentation.screens.LaunchListScreen
import org.rocketserverkmm.project.presentation.screens.SettingsScreen
import org.rocketserverkmm.project.presentation.viewmodels.AppBootstrapViewModel
import org.rocketserverkmm.project.settings.local.AsyncImageLoaderSingleton
import org.rocketserverkmm.project.settings.tabSetting.TabItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val scopeManager = remember { ScopeManager() }
    var selectedTab by remember { mutableStateOf(TabItem.LAUNCHES) }
    koinViewModel<AppBootstrapViewModel>()

    DisposableEffect(Unit) {
        onDispose {
            scopeManager.closeAllScopes()
        }
    }

    CompositionLocalProvider(LocalScopeManager provides scopeManager) {
        RocketReserverKMMTheme {
            setSingletonImageLoaderFactory { context ->
                AsyncImageLoaderSingleton.getAsyncImageLoader(context)
            }

            Navigator(LaunchListScreen()) { navigator ->
                Scaffold(
                    topBar = {
                        Surface(
                            shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
                            color = MaterialTheme.colorScheme.primary,
                        ) {
                            TopAppBar(
                                title = { Text("RocketServerKMM") },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = Color.Transparent,
                                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                                    actionIconContentColor = MaterialTheme.colorScheme.primary,
                                ),
                                navigationIcon = {
                                    if (navigator.canPop) {
                                        IconButton(onClick = { navigator.pop() }) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = "Назад",
                                                tint = MaterialTheme.colorScheme.onPrimary
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    },
                    bottomBar = {
                        NavigationBar {
                            TabItem.values().forEach { tab ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            imageVector = when (tab) {
                                                TabItem.LAUNCHES -> Icons.Default.Add
                                                TabItem.SETTINGS -> Icons.Default.Settings
                                            },
                                            contentDescription = tab.title
                                        )
                                    },
                                    label = { Text(tab.title) },
                                    selected = selectedTab == tab,
                                    onClick = { selectedTab = tab }
                                )
                            }
                        }
                    }
                ) { paddingValues ->
                    Box(Modifier.padding(paddingValues)) {
//                        CurrentScreen()
                        when (selectedTab) {
                            TabItem.LAUNCHES -> CurrentScreen()
                            TabItem.SETTINGS -> SettingsScreen().Content()
                        }
                    }
                }
            }
        }
    }
}