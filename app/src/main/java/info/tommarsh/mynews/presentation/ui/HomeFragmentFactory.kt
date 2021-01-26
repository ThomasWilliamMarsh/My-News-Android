package info.tommarsh.mynews.presentation.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import javax.inject.Inject

class HomeFragmentFactory
@Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            HomeFragment::class.java.name -> HomeFragment(preferencesRepository)
            else -> super.instantiate(classLoader, className)
        }
    }
}