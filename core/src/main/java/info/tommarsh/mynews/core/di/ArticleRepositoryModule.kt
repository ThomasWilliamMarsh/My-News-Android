package info.tommarsh.mynews.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.tommarsh.mynews.core.article.data.ArticleDataRepository
import info.tommarsh.mynews.core.article.data.ArticleRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class ArticleRepositoryModule {

    @Binds
    abstract fun provideArticleRepository(repository: ArticleDataRepository): ArticleRepository
}