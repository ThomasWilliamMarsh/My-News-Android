package info.tommarsh.data.di

import dagger.Binds
import dagger.Module
import info.tommarsh.data.ArticleDataRepository
import info.tommarsh.data.CategoryDataRepository
import info.tommarsh.data.VideoDataRepository
import info.tommarsh.core.repository.ArticleRepository
import info.tommarsh.core.repository.CategoryRepository
import info.tommarsh.core.repository.PreferencesRepository
import info.tommarsh.core.repository.VideoRepository
import info.tommarsh.data.SharedPreferencesRepository

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