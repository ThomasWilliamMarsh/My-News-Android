package info.tommarsh.mynews.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.tommarsh.mynews.core.util.coroutines.CoroutineDispatcherProvider
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    fun provideDispatcherProvider(coroutineDispatcherProvider: CoroutineDispatcherProvider): DispatcherProvider =
        coroutineDispatcherProvider
}