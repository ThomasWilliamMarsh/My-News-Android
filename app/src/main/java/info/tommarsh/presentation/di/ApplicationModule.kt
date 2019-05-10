package info.tommarsh.presentation.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import info.tommarsh.core.coroutines.CoroutineDispatcherProvider
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.data.source.local.NewsDatabase
import info.tommarsh.data.source.local.category.PrePopulater

@Module(subcomponents = [ActivityComponent::class])
object ApplicationModule {


    @Provides
    @JvmStatic
    fun provideDb(app: Context) =
        Room.databaseBuilder(app, NewsDatabase::class.java, "articles-db")
            .addCallback(PrePopulater())
            .build()

    @Provides
    @JvmStatic
    fun provideArticlesDao(db: NewsDatabase) = db.articlesDao()

    @Provides
    @JvmStatic
    fun provideCategoriesDao(db: NewsDatabase) = db.categoriesDao()

    @Provides
    @JvmStatic
    fun provideDispatcherProvider(coroutineDispatcherProvider: CoroutineDispatcherProvider): DispatcherProvider =
        coroutineDispatcherProvider
}
