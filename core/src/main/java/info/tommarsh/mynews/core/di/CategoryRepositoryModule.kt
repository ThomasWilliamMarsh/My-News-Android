package info.tommarsh.mynews.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.tommarsh.mynews.core.category.data.CategoryDataRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryRepositoryModule {

    @Binds
    abstract fun provideCategoryRepository(repository: CategoryDataRepository): CategoryRepository
}