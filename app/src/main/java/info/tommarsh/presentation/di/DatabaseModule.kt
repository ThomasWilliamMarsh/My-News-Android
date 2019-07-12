package info.tommarsh.presentation.di

import android.content.Context
import androidx.room.Room
import dagger.Provides
import info.tommarsh.data.source.local.NewsDatabase
import info.tommarsh.data.source.local.category.PrePopulater

object DatabaseModule {

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
}