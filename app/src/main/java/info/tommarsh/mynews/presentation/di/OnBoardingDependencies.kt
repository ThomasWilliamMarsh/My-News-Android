package info.tommarsh.mynews.presentation.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface OnBoardingDependencies {
    fun preferences(): PreferencesRepository

    fun dispatcherProvider() : DispatcherProvider
}