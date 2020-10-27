package info.tommarsh.mynews.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import info.tommarsh.mynews.core.article.data.ArticleDataRepository
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.category.data.CategoryDataRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.preferences.SharedPreferencesRepository
import info.tommarsh.mynews.core.video.data.VideoDataRepository
import info.tommarsh.mynews.core.video.data.VideoRepository

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideArticleRepository(repository: ArticleDataRepository): ArticleRepository

    @Binds
    abstract fun provideCategoryRepository(repository: CategoryDataRepository): CategoryRepository

    @Binds
    abstract fun provideVideoRepository(repository: VideoDataRepository): VideoRepository

    @Binds
    abstract fun providePreferencesRepository(repository: SharedPreferencesRepository): PreferencesRepository
}