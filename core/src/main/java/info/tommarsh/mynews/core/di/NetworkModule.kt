package info.tommarsh.mynews.core.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.tommarsh.mynews.core.BuildConfig
import info.tommarsh.mynews.core.article.data.remote.source.ArticleApiService
import info.tommarsh.mynews.core.util.NewsApiInterceptor
import info.tommarsh.mynews.core.util.YoutubeApiInterceptor
import info.tommarsh.mynews.core.video.data.remote.source.VideoApiService
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
@InstallIn(SingletonComponent::class)
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


    const val STANDARD_PAGE_SIZE = 20
}