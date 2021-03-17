package info.tommarsh.mynews.di

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.di.RepositoryModule
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.video.data.VideoRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class FakeRepositoryModule {

    @Binds
    abstract fun provideVideoRepository(repository: FakeVideoRepository): VideoRepository

    @Binds
    abstract fun provideArticleRepository(repository: FakeArticleRepository) : ArticleRepository

    @Binds
    abstract fun provideCategoryRepository(repository: FakeCategoryRepository) : CategoryRepository

    @Binds
    abstract fun providePreferencesRepository(repository: FakePreferencesRepository) : PreferencesRepository
}