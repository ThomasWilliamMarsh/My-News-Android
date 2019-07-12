package info.tommarsh.core.di

import dagger.Component
import info.tommarsh.core.coroutines.DispatcherProvider
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class])
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(): CoreComponent
    }

    fun dispatcherProvider(): DispatcherProvider
}