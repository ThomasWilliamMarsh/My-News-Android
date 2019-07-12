package info.tommarsh.core.di

import dagger.Module
import dagger.Provides
import info.tommarsh.core.coroutines.CoroutineDispatcherProvider
import info.tommarsh.core.coroutines.DispatcherProvider


@Module
object CoreModule {

    @Provides
    @JvmStatic
    fun provideDispatcherProvider(coroutineDispatcherProvider: CoroutineDispatcherProvider): DispatcherProvider =
        coroutineDispatcherProvider
}