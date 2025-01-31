package org.rocketserverkmm.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.TabNavigator
import coil3.compose.setSingletonImageLoaderFactory
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.rocketserverkmm.project.di.LocalScopeManager
import org.rocketserverkmm.project.di.ScopeManager
import org.rocketserverkmm.project.platform.RocketReserverKMMTheme
import org.rocketserverkmm.project.presentation.utils.appBar.AppBar
import org.rocketserverkmm.project.presentation.utils.appBar.AppBarState
import org.rocketserverkmm.project.presentation.utils.appBar.LocalAppBarState
import org.rocketserverkmm.project.presentation.utils.bottomBar.BottomBarState
import org.rocketserverkmm.project.presentation.utils.bottomBar.LocalBottomBarState
import org.rocketserverkmm.project.presentation.utils.bottomBar.TabItem
import org.rocketserverkmm.project.presentation.utils.bottomBar.TabNavigationItem
import org.rocketserverkmm.project.presentation.viewmodels.AppBootstrapViewModel
import org.rocketserverkmm.project.settings.local.AsyncImageLoaderSingleton

@Composable
@Preview
fun App() {
    val scopeManager = remember { ScopeManager() }
    val topBarState = remember { mutableStateOf(AppBarState()) }
    val bottomBarState = remember { mutableStateOf(BottomBarState()) }

    koinViewModel<AppBootstrapViewModel>()

    DisposableEffect(Unit) {
        onDispose {
            scopeManager.closeAllScopes()
        }
    }

    CompositionLocalProvider(
        LocalScopeManager provides scopeManager,
        LocalAppBarState provides topBarState,
        LocalBottomBarState provides bottomBarState
    ) {
        RocketReserverKMMTheme {
            setSingletonImageLoaderFactory { context ->
                AsyncImageLoaderSingleton.getAsyncImageLoader(context)
            }

            TabNavigator(TabItem.LaunchesTab) { tabNavigator ->
                Scaffold(
                    topBar = {
                        AppBar(
                            topBarState.value,
                        )
                    },
                    bottomBar = {
                        AnimatedVisibility(
                            visible = bottomBarState.value.visible,
                            enter = slideInVertically(initialOffsetY = { it }),
                            exit = slideOutVertically(targetOffsetY = { it })
                        ) {
                            NavigationBar {
                                TabNavigationItem(TabItem.LaunchesTab)
                                TabNavigationItem(TabItem.SettingsTab)
                            }
                        }
                    }

                ) { paddingValues ->
                    Box(Modifier.padding(paddingValues)) {
                        tabNavigator.current.Content()
                    }
                }
            }
        }
    }
}