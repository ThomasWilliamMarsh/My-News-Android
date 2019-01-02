package info.tommarsh.presentation.di

import dagger.Binds
import dagger.Module
import info.tommarsh.data.ArticleDataRepository
import info.tommarsh.data.CategoryDataRepository
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.domain.source.CategoryRepository

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideArticleRepository(repository: ArticleDataRepository): ArticleRepository

    @Binds
    abstract fun provideCategoryRepository(repository: CategoryDataRepository): CategoryRepository
}