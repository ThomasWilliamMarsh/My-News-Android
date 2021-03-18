package info.tommarsh.mynews.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.preferences.SharedPreferencesRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesRepositoryModule {

    @Binds
    abstract fun providePreferencesRepository(repository: SharedPreferencesRepository): PreferencesRepository
}