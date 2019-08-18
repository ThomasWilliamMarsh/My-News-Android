package info.tommarsh.mynews.repository.di

import dagger.Binds
import dagger.Module
import info.tommarsh.mynews.core.repository.ArticleRepository
import info.tommarsh.mynews.core.repository.CategoryRepository
import info.tommarsh.mynews.core.repository.PreferencesRepository
import info.tommarsh.mynews.core.repository.VideoRepository
import info.tommarsh.mynews.repository.ArticleDataRepository
import info.tommarsh.mynews.repository.CategoryDataRepository
import info.tommarsh.mynews.repository.SharedPreferencesRepository
import info.tommarsh.mynews.repository.VideoDataRepository

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideArticleRepository(repository: ArticleDataRepository): ArticleRepository

    @Binds
    abstract fun provideCategoryRepository(repository: CategoryDataRepository): CategoryRepository

    @Binds
    abstract fun provideVideoRepository(repository: VideoDataRepository): VideoRepository

    @Binds
    abstract fun providePreferencesRepository(repository: SharedPreferencesRepository) : PreferencesRepository
}