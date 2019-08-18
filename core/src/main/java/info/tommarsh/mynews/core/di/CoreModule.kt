package info.tommarsh.mynews.core.di

import dagger.Module
import dagger.Provides
import info.tommarsh.mynews.core.coroutines.CoroutineDispatcherProvider
import info.tommarsh.mynews.core.coroutines.DispatcherProvider


@Module
object CoreModule {

    @Provides
    @JvmStatic
    fun provideDispatcherProvider(coroutineDispatcherProvider: CoroutineDispatcherProvider): DispatcherProvider =
        coroutineDispatcherProvider
}