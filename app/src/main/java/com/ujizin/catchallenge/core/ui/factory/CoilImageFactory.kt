package com.ujizin.catchallenge.core.ui.factory

import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import com.ujizin.catchallenge.R
import com.ujizin.catchallenge.core.data.remote.interceptor.ImageInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoilImageFactory @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageInterceptor: ImageInterceptor
) : ImageLoaderFactory {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(imageInterceptor)
        .build()

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient(okHttpClient)
            .crossfade(true)
            .fallback(R.drawable.ic_cat)
            .error(R.drawable.ic_cat)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.1)
                    .weakReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .directory(File(context.cacheDir, BREED_CACHE_FILE_NAME).apply { mkdirs() })
                    .maxSizePercent(0.001)
                    .build()
            }
            .build()
    }

    companion object {
        private const val BREED_CACHE_FILE_NAME = "breed_cache"
    }
}