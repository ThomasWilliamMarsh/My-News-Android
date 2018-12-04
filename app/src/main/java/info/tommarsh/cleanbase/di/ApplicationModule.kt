package info.tommarsh.cleanbase.di

import dagger.Module
import dagger.Provides
import info.tommarsh.cleanbase.App

@Module
class ApplicationModule(private val app: App) {

    @Provides
    fun provideApplication() = app
}