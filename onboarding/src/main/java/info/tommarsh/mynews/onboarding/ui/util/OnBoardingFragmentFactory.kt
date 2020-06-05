package info.tommarsh.mynews.onboarding.ui.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import info.tommarsh.mynews.core.util.ViewModelFactory
import info.tommarsh.mynews.onboarding.ui.OnBoardingFragment
import javax.inject.Inject

class OnBoardingFragmentFactory
    @Inject constructor(private val viewModelFactory: ViewModelFactory) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return OnBoardingFragment(viewModelFactory)
    }
}