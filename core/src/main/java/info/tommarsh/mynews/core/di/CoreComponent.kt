package info.tommarsh.mynews.core.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.core.video.data.VideoRepository
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CoreModule::class,
        LocalModule::class,
        NetworkModule::class,
        RepositoryModule::class]
)
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun dispatcherProvider(): DispatcherProvider

    fun articleRepository(): ArticleRepository

    fun categoriesRepository(): CategoryRepository

    fun videoRepository(): VideoRepository

    fun sharedPreferences(): PreferencesRepository
}