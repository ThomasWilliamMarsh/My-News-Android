package info.tommarsh.presentation.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import info.tommarsh.data.source.local.ArticlesDatabase

@Module
object ApplicationModule {

    @Provides
    @JvmStatic
    fun provideDb(app: Application) =
        Room.databaseBuilder(app.applicationContext, ArticlesDatabase::class.java, "articles-db").build()

    @Provides
    @JvmStatic
    fun provideDao(db: ArticlesDatabase) = db.articlesDao()
}
