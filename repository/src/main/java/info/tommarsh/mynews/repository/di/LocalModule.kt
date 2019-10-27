package info.tommarsh.mynews.repository.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import info.tommarsh.mynews.repository.source.local.NewsDatabase

@Module
object LocalModule {

    @Provides
    @JvmStatic
    internal fun provideDb(app: Context) =
        Room.databaseBuilder(app, NewsDatabase::class.java, "articles-db")
            .createFromAsset("database/default-db")
            .build()

    @Provides
    @JvmStatic
    internal fun provideArticlesDao(db: NewsDatabase) = db.articlesDao()

    @Provides
    @JvmStatic
    internal fun provideCategoriesDao(db: NewsDatabase) = db.categoriesDao()

    @Provides
    @JvmStatic
    internal fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("default_preferences", Context.MODE_PRIVATE)
}