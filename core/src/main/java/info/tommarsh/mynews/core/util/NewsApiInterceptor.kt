package info.tommarsh.mynews.core.util

import info.tommarsh.mynews.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class NewsApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("x-api-key", BuildConfig.API_KEY)
            .build()

        return chain.proceed(newRequest)
    }
}