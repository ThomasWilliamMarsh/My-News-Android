package info.tommarsh.mynews.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import info.tommarsh.mynews.core.util.coroutines.CoroutineDispatcherProvider
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider

@Module
@InstallIn(ApplicationComponent::class)
object CoreModule {

    @Provides
    fun provideDispatcherProvider(coroutineDispatcherProvider: CoroutineDispatcherProvider): DispatcherProvider =
        coroutineDispatcherProvider
}