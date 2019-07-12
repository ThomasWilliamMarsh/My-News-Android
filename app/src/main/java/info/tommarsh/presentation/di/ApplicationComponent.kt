package info.tommarsh.presentation.di

import dagger.Component
import info.tommarsh.core.di.CoreComponent
import info.tommarsh.data.di.DataComponent
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [CoreComponent::class, DataComponent::class],
    modules = [(ApplicationModule::class), (ViewModelModule::class)]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            dataComponent: DataComponent
        ): ApplicationComponent
    }

    val activityComponent: ActivityComponent.Factory
}