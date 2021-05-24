package info.tommarsh.mynews.onboarding.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import info.tommarsh.mynews.home.di.OnBoardingDependencies
import info.tommarsh.mynews.onboarding.di.modules.DataSourceModule
import info.tommarsh.mynews.onboarding.di.modules.OnBoardingModule
import info.tommarsh.mynews.onboarding.ui.OnBoardingViewModel

@Component(
    dependencies = [OnBoardingDependencies::class],
    modules = [OnBoardingModule::class, DataSourceModule::class]
)
internal interface OnBoardingComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            onBoardingDependencies: OnBoardingDependencies
        ): OnBoardingComponent
    }

    val viewModel: OnBoardingViewModel
}