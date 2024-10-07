package com.ujizin.catchallenge.core.ui.factory

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.toArgb
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.memory.MemoryCache
import coil.request.CachePolicy
import com.ujizin.catchallenge.R
import com.ujizin.catchallenge.core.data.remote.interceptor.ImageInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoilImageFactory @Inject constructor(
    @ApplicationContext private val context: Context,
    imageInterceptor: ImageInterceptor
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
            .placeholder(ColorDrawable(Gray.toArgb()))
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.1)
                    .weakReferencesEnabled(true)
                    .build()
            }
            .build()
    }
}