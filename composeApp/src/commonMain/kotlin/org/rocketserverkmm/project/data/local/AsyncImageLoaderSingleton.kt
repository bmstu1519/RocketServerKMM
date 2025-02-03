package org.rocketserverkmm.project.data.local

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import okio.FileSystem

object AsyncImageLoaderSingleton {
    fun getAsyncImageLoader(context: PlatformContext) =
        ImageLoader.Builder(context).memoryCachePolicy(CachePolicy.ENABLED).memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, 0.3)
                .strongReferencesEnabled(true)
                .build()
        }.diskCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                newDiskCache()
            }.crossfade(true).logger(DebugLogger()).build()

    private fun newDiskCache(): DiskCache {
        return DiskCache.Builder()
            .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
            .maxSizeBytes(1024L * 1024 * 1024) // 512MB
            .build()
    }
}