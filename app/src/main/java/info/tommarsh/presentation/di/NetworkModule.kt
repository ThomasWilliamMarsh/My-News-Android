package info.tommarsh.presentation.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import info.tommarsh.core.network.ApiInterceptor
import info.tommarsh.data.source.remote.ArticleApiService
import info.tommarsh.presentation.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    @Provides
    @JvmStatic
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiInterceptor())
        .build()

    @Provides
    @JvmStatic
    fun provideApi(client: OkHttpClient): ArticleApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(client)
        .build()
        .create(ArticleApiService::class.java)
}