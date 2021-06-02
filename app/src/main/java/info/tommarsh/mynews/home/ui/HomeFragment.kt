package info.tommarsh.mynews.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.navigator.ClickEvent
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.doOnInsets
import info.tommarsh.mynews.home.R
import info.tommarsh.mynews.home.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var preferences: PreferencesRepository

    private lateinit var binding: FragmentHomeBinding

    private val navigationViewModel by activityViewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        listenToClickEvents()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavigation()
        setUpToolbar()
        setWindowInsets()
    }

    private fun setUpToolbar() {
        (requireActivity() as? AppCompatActivity)?.setSupportActionBar(binding.homeToolbar)
    }

    private fun setUpNavigation() {
        val navigator = HomeFragmentMenuNavigator(childFragmentManager, requireContext()) { label ->
            binding.homeToolbar.title = label
        }
        binding.homeBottomNavigation?.setOnItemSelectedListener(navigator)
        binding.homeNavigationRail?.setOnItemSelectedListener(navigator)
        if (preferences.shouldShowOnBoarding()) {
            findNavController().navigate(R.id.action_homeFragment_to_onboardingGraph)
        }
    }

    private fun setWindowInsets() {
        binding.root.doOnInsets { systemBarInsets, navigationBarInsets ->
            binding.homeToolbar.updatePadding(top = systemBarInsets.top)
            binding.homeBottomNavigation?.updatePadding(bottom = navigationBarInsets.bottom)
        }
    }

    private fun listenToClickEvents() = lifecycleScope.launchWhenResumed {
        navigationViewModel.clicks.collectLatest { event ->
            when (event) {
                is ClickEvent.Search -> findNavController().navigate(
                    R.id.action_homeFragment_to_search_graph
                )
                is ClickEvent.Categories -> findNavController().navigate(
                    R.id.action_homeFragment_to_category_choice_graph
                )
                is ClickEvent.Article -> findNavController().navigate(
                    R.id.action_homeFragment_to_article_nav_graph,
                    bundleOf(
                        "url" to event.imageUrl,
                        "title" to event.title,
                        "webUrl" to event.webUrl
                    )
                )
                is ClickEvent.OnBoarding -> findNavController().navigate(
                    R.id.action_homeFragment_to_onboardingGraph,
                )
            }
        }
    }
}