package info.tommarsh.cleanbase.di

import dagger.Component
import info.tommarsh.cleanbase.ViewModelFactory
import info.tommarsh.domain.source.IRepository

@Component(modules = [(ApplicationModule::class), (NetworkModule::class), (ViewModelModule::class)])
interface ApplicationComponent {
    fun repository(): IRepository

    fun viewModelFactory(): ViewModelFactory
}