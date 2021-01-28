package info.tommarsh.mynews.onboarding.ui

import android.content.Context
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.EntryPointAccessors
import info.tommarsh.mynews.onboarding.di.DaggerOnBoardingComponent
import info.tommarsh.mynews.onboarding.di.OnBoardingComponent
import info.tommarsh.mynews.presentation.di.OnBoardingDependencies

internal fun Fragment.onBoardingViewModel(
    @IdRes navGraphId: Int
) = navGraphViewModels<OnBoardingViewModel>(navGraphId) {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return OnboardingComponentCreator.create(requireContext()).viewModel as T
        }
    }
}


private object OnboardingComponentCreator {

    private var component : OnBoardingComponent? = null

    fun create(context: Context) : OnBoardingComponent {
        if(component == null) {
            component =   DaggerOnBoardingComponent.factory()
                .create(
                    context,
                    EntryPointAccessors.fromApplication(
                        context.applicationContext,
                        OnBoardingDependencies::class.java
                    )
                )
        }

        return component!!
    }
}