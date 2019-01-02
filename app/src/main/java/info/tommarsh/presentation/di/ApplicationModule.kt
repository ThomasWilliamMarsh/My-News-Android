package info.tommarsh.presentation.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import info.tommarsh.data.source.local.NewsDatabase

@Module
object ApplicationModule {


    @Provides
    @JvmStatic
    fun provideDb(app: Application) =
        Room.databaseBuilder(app.applicationContext, NewsDatabase::class.java, "articles-db")
            .build()

    @Provides
    @JvmStatic
    fun provideArticlesDao(db: NewsDatabase) = db.articlesDao()

    @Provides
    @JvmStatic
    fun provideCategoriesDao(db: NewsDatabase) = db.categoriesDao()
}
