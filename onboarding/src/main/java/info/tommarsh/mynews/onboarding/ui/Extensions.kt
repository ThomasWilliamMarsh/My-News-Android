package info.tommarsh.mynews.onboarding.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels

internal inline fun Fragment.onBoardingViewModel(
    @IdRes navGraphId: Int
) = navGraphViewModels<OnBoardingViewModel>(navGraphId) {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return (requireContext() as OnBoardingActivity).component.viewModel as T
        }
    }
}