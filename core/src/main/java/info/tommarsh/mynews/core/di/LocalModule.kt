package info.tommarsh.mynews.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import info.tommarsh.mynews.core.database.NewsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    internal fun provideDb(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, NewsDatabase::class.java, "articles-db")
            .createFromAsset("database/prepackaged.db")
            .build()

    @Provides
    internal fun provideArticlesDao(db: NewsDatabase) = db.articlesDao()

    @Provides
    internal fun provideCategoriesDao(db: NewsDatabase) = db.categoriesDao()

    @Provides
    internal fun providePagingDao(db: NewsDatabase) = db.pagingDao()

    @Provides
    internal fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("default_preferences", Context.MODE_PRIVATE)
}