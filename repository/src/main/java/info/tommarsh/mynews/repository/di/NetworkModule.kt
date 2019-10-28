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
import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class NewsClient

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class YoutubeClient

@Module
object NetworkModule {

    @NewsClient
    @Provides
    internal fun provideNewsOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(NewsApiInterceptor())
        .build()

    @YoutubeClient
    @Provides
    internal fun provideYoutubeOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(YoutubeApiInterceptor())
        .build()


    @Provides
    internal fun provideNewsApi(@NewsClient client: OkHttpClient): ArticleApiService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
            .create(ArticleApiService::class.java)

    @Provides
    internal fun provideYoutubeApi(@YoutubeClient client: OkHttpClient): VideoApiService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.YOUTUBE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
            .create(VideoApiService::class.java)
}