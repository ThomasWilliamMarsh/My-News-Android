package info.tommarsh.mynews.core.di

import dagger.Module
import dagger.Provides
import info.tommarsh.mynews.core.util.coroutines.CoroutineDispatcherProvider
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider


@Module
object CoreModule {

    @Provides
    fun provideDispatcherProvider(coroutineDispatcherProvider: CoroutineDispatcherProvider): DispatcherProvider =
        coroutineDispatcherProvider
}