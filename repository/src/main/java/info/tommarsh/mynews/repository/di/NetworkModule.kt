package info.tommarsh.mynews.repository.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import info.tommarsh.mynews.repository.source.remote.articles.ArticleApiService
import info.tommarsh.mynews.repository.source.remote.videos.VideoApiService
import info.tommarsh.mynews.repository.util.NewsApiInterceptor
import info.tommarsh.mynews.repository.util.YoutubeApiInterceptor
import info.tommarsh.repository.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
object NetworkModule {

    @Provides
    @JvmStatic
    @Named("NEWS_CLIENT")
    internal fun provideNewsOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(NewsApiInterceptor())
        .build()

    @Provides
    @JvmStatic
    @Named("YOUTUBE_CLIENT")
    internal fun provideYoutubeOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(YoutubeApiInterceptor())
        .build()


    @Provides
    @JvmStatic
    internal fun provideNewsApi(@Named("NEWS_CLIENT") client: OkHttpClient): ArticleApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(client)
        .build()
        .create(ArticleApiService::class.java)

    @Provides
    @JvmStatic
    internal fun provideYoutubeApi(@Named("YOUTUBE_CLIENT") client: OkHttpClient): VideoApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.YOUTUBE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(client)
        .build()
        .create(VideoApiService::class.java)
}