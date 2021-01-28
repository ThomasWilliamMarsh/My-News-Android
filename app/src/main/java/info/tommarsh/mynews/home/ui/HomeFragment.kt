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
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.navigator.ClickEvent
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.doOnInsets
import info.tommarsh.mynews.home.R
import info.tommarsh.mynews.home.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment: Fragment(), NavController.OnDestinationChangedListener {

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
        val navHost =
            childFragmentManager.findFragmentById(R.id.home_nav_host) as NavHostFragment
        val controller = navHost.navController
        if (preferences.shouldShowOnBoarding()) {
            navigationViewModel.dispatchClick(ClickEvent.OnBoarding)
        }
        controller.addOnDestinationChangedListener(this)
        binding.homeBottomNavigation.setupWithNavController(controller)
    }

    private fun setWindowInsets() {
        binding.root.doOnInsets { systemBarInsets, navigationBarInsets ->
            binding.homeToolbar.updatePadding(top = systemBarInsets.top)
            binding.homeBottomNavigation.updatePadding(bottom = navigationBarInsets.bottom)
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        binding.homeToolbar.title = destination.label
    }

    private fun listenToClickEvents() = lifecycleScope.launchWhenCreated {
        navigationViewModel.clicks.collectLatest { event ->
            when (event) {
                is ClickEvent.Search -> {
                    findNavController().navigate(
                        R.id.action_homeFragment_to_search_graph
                    )
                }
                is ClickEvent.Categories -> {
                    findNavController().navigate(
                        R.id.action_homeFragment_to_category_choice_graph
                    )
                }
                is ClickEvent.Article -> {
                    findNavController().navigate(
                        R.id.action_homeFragment_to_articleFragment,
                        bundleOf("url" to event.id, "title" to event.title)
                    )
                }
                is ClickEvent.OnBoarding -> {
                    findNavController().navigate(
                        R.id.action_homeFragment_to_onboardingGraph,
                    )
                }
            }
        }
    }
}