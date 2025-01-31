package org.rocketserverkmm.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.TabNavigator
import coil3.compose.setSingletonImageLoaderFactory
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.rocketserverkmm.project.di.LocalScopeManager
import org.rocketserverkmm.project.di.ScopeManager
import org.rocketserverkmm.project.platform.RocketReserverKMMTheme
import org.rocketserverkmm.project.presentation.viewmodels.AppBootstrapViewModel
import org.rocketserverkmm.project.settings.local.AsyncImageLoaderSingleton
import org.rocketserverkmm.project.settings.tabSetting.TabItem
import org.rocketserverkmm.project.settings.tabSetting.TabNavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val scopeManager = remember { ScopeManager() }

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

            TabNavigator(TabItem.LaunchesTab) { navigator ->
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
                            )
                        }
                    },
                    bottomBar = {
                        NavigationBar {
                            TabNavigationItem(TabItem.LaunchesTab)
                            TabNavigationItem(TabItem.SettingsTab)
                        }
                    }
                ) { paddingValues ->
                    Box(Modifier.padding(paddingValues)) {
                        navigator.current.Content()
                    }
                }
            }
        }
    }
}