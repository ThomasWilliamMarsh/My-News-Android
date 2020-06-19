package info.tommarsh.mynews.onboarding.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import info.tommarsh.mynews.core.di.ViewModelKey
import info.tommarsh.mynews.onboarding.ui.OnBoardingViewModel

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(OnBoardingViewModel::class)
    abstract fun bindOnBoardingViewModel(onBoardingViewModel: OnBoardingViewModel): ViewModel
}