package org.rocketserverkmm.project.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.rocketserverkmm.project.di.LocalScopeManager
import org.rocketserverkmm.project.di.ScopeFlow
import org.rocketserverkmm.project.di.modules.LaunchListData
import org.rocketserverkmm.project.domain.model.launchList.Launch
import org.rocketserverkmm.project.presentation.states.LaunchListAction
import org.rocketserverkmm.project.presentation.states.LaunchListDestination
import org.rocketserverkmm.project.presentation.viewmodels.LaunchListViewModel
import rocketserverkmm.composeapp.generated.resources.Res
import rocketserverkmm.composeapp.generated.resources.baseline_error_24
import rocketserverkmm.composeapp.generated.resources.ic_placeholder
import rocketserverkmm.composeapp.generated.resources.rocket_image_stub

class LaunchListScreen : Screen {

    @Composable
    override fun Content() {
        val scopeManager = LocalScopeManager.current
        val scope = remember { scopeManager.getOrCreateScope(ScopeFlow.LAUNCH_FLOW) }
        val launchListData = remember { scope.get<LaunchListData>() }

        val viewModel: LaunchListViewModel = koinViewModel<LaunchListViewModel>()
        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            viewModel.actionToDestination(LaunchListAction.Load)
        }

        LazyColumn {
            items(state.launches) { launch ->
                LaunchItems(launch) {
                    viewModel.actionToDestination(
                        LaunchListAction.NavigateToDetails(
                            launch.id
                        )
                    )
                }
            }
            item {
                if (state.hasMore) {
                    LoadingItems()
                    viewModel.actionToDestination(LaunchListAction.LoadMore)
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.destination.collect { destination ->
                when (destination) {
                    is LaunchListDestination.LaunchDetails -> {
                        launchListData.launchId = destination.launchId
                        navigator.push(
                            LaunchDetailsScreen()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LaunchItems(
    launch: Launch,
    onItemClick: () -> Unit
) {
    ListItem(
        modifier = Modifier.clickable(onClick = onItemClick),
        headlineContent = { Text(text = launch.missionName.orEmpty()) },
        supportingContent = { Text(text = launch.site.orEmpty()) },
        leadingContent = {
            AsyncImage(
                modifier = Modifier.size(68.dp),
                model = launch.missionPatchUrl,
                error = painterResource(Res.drawable.rocket_image_stub),
                contentDescription = "Mission patch"
            )
        }
    )
}

@Composable
private fun LoadingItems() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        CircularProgressIndicator()
    }
}
