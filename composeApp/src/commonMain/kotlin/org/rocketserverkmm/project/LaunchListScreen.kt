
package org.rocketserverkmm.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Optional
import org.jetbrains.compose.resources.painterResource
import rocketserverkmm.composeapp.generated.resources.Res
import rocketserverkmm.composeapp.generated.resources.ic_placeholder

class LaunchListScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        var cursor: String? by remember { mutableStateOf(null) }
        var response: ApolloResponse<LaunchListQuery.Data>? by remember { mutableStateOf(null) }
        var launchList by remember { mutableStateOf(emptyList<LaunchListQuery.Launch>()) }
        LaunchedEffect(cursor) {
            response = ProvideApolloClientSingleton.apolloClient.query(LaunchListQuery(Optional.present(cursor))).execute()
            launchList = launchList + response?.data?.launches?.launches?.filterNotNull().orEmpty()
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(launchList) { launch ->
                LaunchItem(launch = launch, navigator)
            }

            item {
                if (response?.data?.launches?.hasMore == true) {
                    LoadingItem()
                    cursor = response?.data?.launches?.cursor
                }
            }
        }
    }
}

@Composable
private fun LaunchItem(launch: LaunchListQuery.Launch, navigator: Navigator) {

    ListItem(
        modifier = Modifier.clickable {
            navigator.push(LaunchDetailsScreen(launchId = launch.id))
        },
        headlineContent = {
            // Mission name
            Text(text = launch.mission?.name ?: "")
        },
        supportingContent = {
            // Site
            Text(text = launch.site ?: "")
        },
        leadingContent = {
            // Mission patch
            AsyncImage(
                modifier = Modifier.size(68.dp, 68.dp),
                model = launch.mission?.missionPatch,
                placeholder = painterResource(Res.drawable.ic_placeholder),
                error = painterResource(Res.drawable.ic_placeholder),
                contentDescription = "Mission patch"
            )
        }
    )
}

@Composable
private fun LoadingItem() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CircularProgressIndicator()
    }
}
