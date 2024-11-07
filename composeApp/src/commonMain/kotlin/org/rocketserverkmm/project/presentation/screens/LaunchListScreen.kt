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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.rocketserverkmm.project.dependencies.DependencyProvider
import org.rocketserverkmm.project.dependencies.ViewModelFactory
import org.rocketserverkmm.project.domain.models.launchList.LaunchDTO
import org.rocketserverkmm.project.presentation.states.LaunchListAction
import org.rocketserverkmm.project.presentation.states.LaunchListDestination
import org.rocketserverkmm.project.presentation.viewmodels.LaunchListViewModel
import rocketserverkmm.composeapp.generated.resources.Res
import rocketserverkmm.composeapp.generated.resources.baseline_error_24
import rocketserverkmm.composeapp.generated.resources.ic_placeholder

class LaunchListScreen : Screen {
    @Composable
    override fun Content() {
        val viewModelFactory = ViewModelFactory(DependencyProvider)
        val viewModel: LaunchListViewModel = viewModel(
            factory = viewModelFactory,
            viewModelStoreOwner = LocalViewModelStoreOwner.current
                ?: error("No ViewModelStoreOwner provided")
        )

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
                    is LaunchListDestination.LaunchDetails -> navigator.push(
                        LaunchDetailsScreen(
                            destination.launchId
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun LaunchItems(
    launch: LaunchDTO,
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
                placeholder = painterResource(Res.drawable.ic_placeholder),
                error = painterResource(Res.drawable.baseline_error_24),
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
