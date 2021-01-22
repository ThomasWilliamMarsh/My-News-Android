package info.tommarsh.mynews.presentation.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import info.tommarsh.mynews.core.navigator.ClickDispatcher
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.presentation.ui.categories.CategoriesFragment
import info.tommarsh.mynews.presentation.ui.top.TopNewsFragment
import info.tommarsh.mynews.presentation.ui.videos.VideosFragment
import javax.inject.Inject

class HomeFragmentFactory
    @Inject constructor(
        private val preferencesRepository: PreferencesRepository,
        private val dispatcher: ClickDispatcher
    ): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            HomeFragment::class.java.name -> HomeFragment(dispatcher, preferencesRepository)
            TopNewsFragment::class.java.name -> TopNewsFragment(dispatcher)
            CategoriesFragment::class.java.name -> CategoriesFragment(dispatcher)
            VideosFragment::class.java.name -> VideosFragment(dispatcher)
            else -> super.instantiate(classLoader, className)
        }
    }
}