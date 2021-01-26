package info.tommarsh.mynews.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.navigator.ClickEvent
import info.tommarsh.presentation.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: HomeFragmentFactory

    private val viewModel by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_home)

        listenToClickEvents()
    }

    override fun onBackPressed() {
        if (!findNavController(R.id.nav_host_fragment).navigateUp()) {
            super.onBackPressed()
        }
    }

    private fun listenToClickEvents() = lifecycleScope.launch {
        viewModel.clicks.collectLatest { event ->
            when (event) {
                is ClickEvent.Search -> {
                    findNavController(R.id.nav_host_fragment).navigate(
                        R.id.action_homeFragment_to_search_graph
                    )
                }
                is ClickEvent.Categories -> {
                    findNavController(R.id.nav_host_fragment).navigate(
                        R.id.action_homeFragment_to_category_choice_graph
                    )
                }
                is ClickEvent.Article -> {
                    findNavController(R.id.nav_host_fragment).navigate(
                        R.id.action_homeFragment_to_articleActivity,
                        bundleOf("url" to event.id, "title" to event.title)
                    )
                }
                is ClickEvent.OnBoarding -> {
                    findNavController(R.id.nav_host_fragment).navigate(
                        R.id.action_homeFragment_to_onBoardingActivity
                    )
                    finish()
                }
            }
        }
    }
}
