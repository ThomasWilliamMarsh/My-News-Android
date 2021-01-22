package info.tommarsh.mynews.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.navigator.ClickDispatcher
import info.tommarsh.mynews.core.navigator.ClickEvent
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), NavController.OnDestinationChangedListener {

    @Inject
    lateinit var dispatcher: ClickDispatcher

    @Inject
    lateinit var preferences: PreferencesRepository

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
            lifecycleScope.launch { dispatcher.dispatch(ClickEvent.OnBoarding) }
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
}