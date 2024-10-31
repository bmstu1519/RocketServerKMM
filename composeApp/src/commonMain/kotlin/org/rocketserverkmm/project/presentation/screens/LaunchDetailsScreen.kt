package org.rocketserverkmm.project.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import com.apollographql.apollo.exception.ApolloNetworkException
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.rocketserverkmm.project.BookTripMutation
import org.rocketserverkmm.project.CancelTripMutation
import org.rocketserverkmm.project.KEY_TOKEN
import org.rocketserverkmm.project.LaunchDetailsQuery
import org.rocketserverkmm.project.dependencies.DependencyProvider
import rocketserverkmm.composeapp.generated.resources.Res
import rocketserverkmm.composeapp.generated.resources.baseline_error_24
import rocketserverkmm.composeapp.generated.resources.ic_placeholder

private sealed interface LaunchDetailsState {
    object Loading : LaunchDetailsState
    data class Error(val message: String) : LaunchDetailsState
    data class Success(val data: LaunchDetailsQuery.Data) : LaunchDetailsState
}

data class LaunchDetailsScreen(val launchId: String) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var state by remember { mutableStateOf<LaunchDetailsState>(LaunchDetailsState.Loading) }
        LaunchedEffect(Unit) {
            val response = DependencyProvider.apolloClient.query(LaunchDetailsQuery(launchId)).execute()
            state = when {
                response.errors.orEmpty().isNotEmpty() -> {
                    // GraphQL error
                    LaunchDetailsState.Error(response.errors!!.first().message)
                }
                response.exception is ApolloNetworkException -> {
                    // Network error
                    LaunchDetailsState.Error("Please check your network connectivity.")
                }
                response.data != null -> {
                    // data (never partial)
                    LaunchDetailsState.Success(response.data!!)
                }
                else -> {
                    // Another fetch error, maybe a cache miss?
                    // Or potentially a non-compliant server returning data: null without an error
                    LaunchDetailsState.Error("Oh no... An error happened.")
                }
            }
        }
        when (val s = state) {
            LaunchDetailsState.Loading -> Loading()
            is LaunchDetailsState.Error -> ErrorMessage(s.message)
            is LaunchDetailsState.Success -> LaunchDetails(s.data, navigator)
        }
    }
}

@Composable
private fun LaunchDetails(
    data: LaunchDetailsQuery.Data,
    navigator: Navigator
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Mission patch
            AsyncImage(
                modifier = Modifier.size(160.dp, 160.dp),
                model = data.launch?.mission?.missionPatch,
                placeholder = painterResource(Res.drawable.ic_placeholder),
                error = painterResource(Res.drawable.baseline_error_24),
                contentDescription = "Mission patch"
            )

            Spacer(modifier = Modifier.size(16.dp))

            Column {
                // Mission name
                Text(
                    style = MaterialTheme.typography.headlineMedium,
                    text = data.launch?.mission?.name ?: ""
                )

                // Rocket name
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    text = data.launch?.rocket?.name?.let { "🚀 $it" } ?: "",
                )

                // Site
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    text = data.launch?.site ?: "",
                )
            }
        }
        // Book button
        var loading by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        var isBooked by remember { mutableStateOf(data.launch?.isBooked == true) }
        Button(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(),
            enabled = !loading,
            onClick = {
                loading = true
                scope.launch {
                    val ok = onBookButtonClick(
                        launchId = data.launch?.id ?: "",
                        isBooked = isBooked,
                        navigator = navigator
                    )
                    if (ok) {
                        isBooked = !isBooked
                    }
                    loading = false
                }
            }
        ) {
            if (loading) {
                SmallLoading()
            } else {
                Text(text = if (!isBooked) "Book now" else "Cancel booking")
            }
        }
    }
}

private suspend fun onBookButtonClick(
    launchId: String,
    isBooked: Boolean,
    navigator: Navigator
): Boolean {
    if (DependencyProvider.getKeyVaultClient().getToken(KEY_TOKEN) == null) {
        navigator.push(LoginScreen())
        return false
    }
    val mutation = if (isBooked) {
        CancelTripMutation(id = launchId)
    } else {
        BookTripMutation(id = launchId)
    }
    val response = DependencyProvider.apolloClient.mutation(mutation).execute()
    return if (response.data != null) {
        true
    } else {
        if (response.exception != null) {
            println("LaunchDetails: Failed to book/cancel trip $response.exception")
            false
        } else {
            println("LaunchDetails: Failed to book/cancel trip: ${response.errors!![0].message}")
            false
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
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
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
