package info.tommarsh.mynews.core.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.tommarsh.mynews.core.util.coroutines.CoroutineDispatcherProvider
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.core.util.coroutines.PagingCache
import info.tommarsh.mynews.core.util.coroutines.PagingCacheImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    @Binds
    abstract fun provideDispatcherProvider(coroutineDispatcherProvider: CoroutineDispatcherProvider): DispatcherProvider

    @Binds
    abstract fun providesPagingCache(pagingCacheImpl: PagingCacheImpl): PagingCache
}