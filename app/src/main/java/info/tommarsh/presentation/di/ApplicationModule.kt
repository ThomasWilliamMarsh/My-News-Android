package info.tommarsh.presentation.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import info.tommarsh.data.ArticleDataRepository
import info.tommarsh.data.source.local.ArticlesDatabase
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.presentation.App

@Module
class ApplicationModule(private val app: App) {

    @Provides
    fun provideContext() = app.applicationContext

    @Provides
    fun provideDb(app: Context) = Room.databaseBuilder(app, ArticlesDatabase::class.java, "articles-db").build()

    @Provides
    fun provideDao(db: ArticlesDatabase) = db.articlesDao()

    @Provides
    fun provideRepository(repository: ArticleDataRepository): ArticleRepository =
        repository
}