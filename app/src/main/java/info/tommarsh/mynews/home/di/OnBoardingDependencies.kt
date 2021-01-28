package info.tommarsh.mynews.home.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider

@EntryPoint
@InstallIn(SingletonComponent::class)
interface OnBoardingDependencies {
    fun preferences(): PreferencesRepository

    fun dispatcherProvider(): DispatcherProvider
}