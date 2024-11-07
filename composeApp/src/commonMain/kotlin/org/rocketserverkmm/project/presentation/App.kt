package org.rocketserverkmm.project.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.apollographql.apollo.api.ApolloResponse
import okio.FileSystem
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.rocketserverkmm.project.TripsBookedSubscription
import org.rocketserverkmm.project.dependencies.DependencyProvider
import org.rocketserverkmm.project.presentation.screens.LaunchListScreen
import org.rocketserverkmm.project.theme.RocketReserverKMMTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    RocketReserverKMMTheme {
        setSingletonImageLoaderFactory { context ->
            getAsyncImageLoader(context)
        }

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
                            titleContentColor = Color.Black,
                            navigationIconContentColor = Color.Black,
                            actionIconContentColor = MaterialTheme.colorScheme.primary,
                        ),
                    )
                }
            }
        ) { paddingValues ->
            Box(Modifier.padding(paddingValues)) {
                Navigator(
                    LaunchListScreen()
                )
            }
        }
    }
}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).memoryCachePolicy(CachePolicy.ENABLED).memoryCache {
        MemoryCache.Builder().maxSizePercent(context, 0.3).strongReferencesEnabled(true).build()
    }.diskCachePolicy(CachePolicy.ENABLED).networkCachePolicy(CachePolicy.ENABLED).diskCache {
        newDiskCache()
    }.crossfade(true).logger(DebugLogger()).build()

fun newDiskCache(): DiskCache {
    return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes(1024L * 1024 * 1024) // 512MB
        .build()
}