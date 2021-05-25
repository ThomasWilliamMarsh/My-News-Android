package info.tommarsh.mynews.onboarding.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.EntryPointAccessors
import info.tommarsh.mynews.home.R.id.onboarding_nav_graph
import info.tommarsh.mynews.home.di.OnBoardingDependencies
import info.tommarsh.mynews.onboarding.di.DaggerOnBoardingComponent
import info.tommarsh.mynews.onboarding.di.OnBoardingComponent

private fun Fragment.createComponent(): OnBoardingComponent {
    val context = requireContext()
    return DaggerOnBoardingComponent.factory()
        .create(
            context,
            EntryPointAccessors.fromApplication(
                context.applicationContext,
                OnBoardingDependencies::class.java
            )
        )
}

@Suppress("UNCHECKED_CAST")
private fun Fragment.onBoardingViewModelFactory(): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return createComponent().viewModel as T
        }
    }
}

internal fun Fragment.onBoardingViewModel() =
    navGraphViewModels<OnBoardingViewModel>(onboarding_nav_graph) {
        onBoardingViewModelFactory()
    }