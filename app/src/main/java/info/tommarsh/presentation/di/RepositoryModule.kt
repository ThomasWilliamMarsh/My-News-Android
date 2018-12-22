package info.tommarsh.presentation.di

import dagger.Binds
import dagger.Module
import info.tommarsh.data.ArticleDataRepository
import info.tommarsh.domain.source.ArticleRepository

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(repository: ArticleDataRepository): ArticleRepository
}