package info.tommarsh.presentation.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class), (NetworkModule::class), (ViewModelModule::class), (RepositoryModule::class)])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    val activityComponent: ActivityComponent.Factory
}