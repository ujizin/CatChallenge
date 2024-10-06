package com.ujizin.catchallenge.core.data.remote.interceptor

import com.ujizin.catchallenge.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageInterceptor @Inject constructor() : Interceptor {

    private enum class ImageFormat(val format: String) {
        JPG("jpg"),
        PNG("png"),
    }

    private tailrec fun Interceptor.Chain.resolveImageFormatRequest(
        originalRequest: Request,
        formats: List<ImageFormat> = ImageFormat.entries,
        currentFormat: ImageFormat = formats.first(),
    ): Response {
        val newRequest = originalRequest.newBuilder()
            .url("${originalRequest.url}.${currentFormat.format}")
            .build()

        val response = proceed(newRequest)
        return when {
            response.isSuccessful -> response
            else -> {
                val nextFormat = formats.getOrNull(formats.indexOf(currentFormat) + 1)
                if (nextFormat != null) {
                    response.close()
                    resolveImageFormatRequest(originalRequest, formats, nextFormat)
                } else {
                    response
                }
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val isCDNRequest = "${request.url}".contains(BuildConfig.CDN_URL)

        if (isCDNRequest) {
            return chain.resolveImageFormatRequest(request)
        }

        return chain.proceed(request)
    }

}
