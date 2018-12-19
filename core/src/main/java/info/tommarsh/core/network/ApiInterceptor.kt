package info.tommarsh.core.network

import info.tommarsh.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("x-api-key", BuildConfig.API_KEY)
            .build()

        return chain.proceed(newRequest)
    }
}