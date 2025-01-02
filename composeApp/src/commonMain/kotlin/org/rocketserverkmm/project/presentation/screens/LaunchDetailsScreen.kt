package org.rocketserverkmm.project.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import org.rocketserverkmm.project.presentation.states.ButtonState
import org.rocketserverkmm.project.presentation.states.LaunchDetailsAction
import org.rocketserverkmm.project.presentation.states.LaunchDetailsDestination
import org.rocketserverkmm.project.presentation.viewmodels.LaunchDetailsViewModel
import rocketserverkmm.composeapp.generated.resources.Res
import rocketserverkmm.composeapp.generated.resources.baseline_error_24
import rocketserverkmm.composeapp.generated.resources.ic_placeholder


class LaunchDetailsScreen : Screen {

    @Composable
    override fun Content() {
        val scopeManager = LocalScopeManager.current
        val scope = remember { scopeManager.getScope(ScopeFlow.LAUNCH_FLOW) }
        val launchListData = remember { scope.get<LaunchListData>() }

        val viewModel: LaunchDetailsViewModel = koinViewModel<LaunchDetailsViewModel>()

        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(Unit) {
            viewModel.actionToDestination(LaunchDetailsAction.Load(launchListData.launchId))
        }

        LaunchedEffect(Unit) {
            viewModel.destination.collect { destination ->
                when (destination) {
                    LaunchDetailsDestination.GoToLogin -> navigator.push(LoginScreen())
                }
            }
        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) {
            LaunchedEffect(Unit) {
                viewModel.snackbarMessage.collect { subscribeMessage ->
                    snackbarHostState.showSnackbar(
                        message = subscribeMessage,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    modifier = Modifier.size(160.dp, 160.dp),
                    model = state.mission?.missionPatch,
                    placeholder = painterResource(Res.drawable.ic_placeholder),
                    error = painterResource(Res.drawable.baseline_error_24),
                    contentDescription = "Mission patch"
                )

                Spacer(modifier = Modifier.size(16.dp))

                Column {
                    Text(
                        style = MaterialTheme.typography.headlineMedium,
                        text = state.mission?.name ?: ""
                    )

                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        text = state.rocket?.name?.let { "🚀 $it" } ?: "",
                    )

                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        style = MaterialTheme.typography.titleMedium,
                        text = state.site ?: "",
                    )
                }
            }

            Button(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(),
                onClick = {
                    viewModel.actionToDestination(LaunchDetailsAction.ClickBookButton)
                },
                shape = RoundedCornerShape(8.dp)
            ) {
                when (state.buttonState) {
                    ButtonState.Error -> Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Error",
                        tint = Color.White
                    )

                    ButtonState.Loading -> SmallLoading()

                    else -> {
                        state.isBooked?.let { isBooked ->
                            Text(text = if (!isBooked) "Book now" else "Cancel booking")
                        }
                    }
                }
            }
        }


    }
}

@Composable
private fun ErrorMessage(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text)
    }
}

@Composable
private fun SmallLoading() {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        color = LocalContentColor.current,
        strokeWidth = 2.dp,
    )
}

//@Preview(showBackground = true)
//@Composable
//private fun LaunchDetailsPreview() {
//    LaunchDetails(launchId = "42")
//}
