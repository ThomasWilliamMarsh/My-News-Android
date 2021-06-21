package info.tommarsh.mynews.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.doOnInsets
import info.tommarsh.mynews.home.R
import info.tommarsh.mynews.home.databinding.FragmentHomeBinding
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

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
}