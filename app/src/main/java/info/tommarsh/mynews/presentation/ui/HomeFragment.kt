package info.tommarsh.mynews.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.navigator.ClickEvent
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment(
    private val preferences: PreferencesRepository
) : Fragment(), NavController.OnDestinationChangedListener {

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

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        binding.homeToolbar.title = destination.label
    }

    private fun listenToClickEvents() = lifecycleScope.launchWhenResumed {
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
                        R.id.action_homeFragment_to_articleActivity,
                        bundleOf("url" to event.id, "title" to event.title)
                    )
                }
                is ClickEvent.OnBoarding -> {
                    findNavController().navigate(
                        R.id.action_homeFragment_to_onBoardingActivity
                    )
                    requireActivity().finish()
                }
            }
        }
    }
}