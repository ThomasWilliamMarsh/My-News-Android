package info.tommarsh.presentation.di

import dagger.Component
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.presentation.ViewModelFactory

@Component(modules = [(ApplicationModule::class), (NetworkModule::class), (ViewModelModule::class)])
interface ApplicationComponent {
    fun repository(): ArticleRepository

    fun viewModelFactory(): ViewModelFactory
}