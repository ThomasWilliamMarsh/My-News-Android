package info.tommarsh.mynews.repository.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import info.tommarsh.mynews.core.repository.ArticleRepository
import info.tommarsh.mynews.core.repository.CategoryRepository
import info.tommarsh.mynews.core.repository.PreferencesRepository
import info.tommarsh.mynews.core.repository.VideoRepository

@Component(modules = [NetworkModule::class, LocalModule::class, RepositoryModule::class])
interface RepositoryComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): RepositoryComponent
    }

    fun articleRepository(): ArticleRepository

    fun categoriesRepository(): CategoryRepository

    fun videoRepository(): VideoRepository

    fun sharedPreferences(): PreferencesRepository
}