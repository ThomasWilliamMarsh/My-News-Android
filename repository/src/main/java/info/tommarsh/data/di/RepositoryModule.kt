package info.tommarsh.data.di

import dagger.Binds
import dagger.Module
import info.tommarsh.data.ArticleDataRepository
import info.tommarsh.data.CategoryDataRepository
import info.tommarsh.data.VideoDataRepository
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.domain.source.CategoryRepository
import info.tommarsh.domain.source.VideoRepository

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideArticleRepository(repository: ArticleDataRepository): ArticleRepository

    @Binds
    abstract fun provideCategoryRepository(repository: CategoryDataRepository): CategoryRepository

    @Binds
    abstract fun provideVideoRepository(repository: VideoDataRepository): VideoRepository
}