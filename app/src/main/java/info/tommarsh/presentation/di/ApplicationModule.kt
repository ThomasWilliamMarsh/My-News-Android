package info.tommarsh.presentation.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import info.tommarsh.data.source.local.NewsDatabase
import info.tommarsh.data.source.local.category.PrePopulater

@Module
object ApplicationModule {


    @Provides
    @JvmStatic
    fun provideDb(app: Application) =
        Room.databaseBuilder(app.applicationContext, NewsDatabase::class.java, "articles-db")
            .addCallback(PrePopulater())
            .build()

    @Provides
    @JvmStatic
    fun provideArticlesDao(db: NewsDatabase) = db.articlesDao()

    @Provides
    @JvmStatic
    fun provideCategoriesDao(db: NewsDatabase) = db.categoriesDao()
}
