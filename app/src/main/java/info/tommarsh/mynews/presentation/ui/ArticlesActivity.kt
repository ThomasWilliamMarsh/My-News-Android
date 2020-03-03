package info.tommarsh.mynews.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import info.tommarsh.mynews.core.di.provideCoreComponent
import info.tommarsh.mynews.presentation.di.DaggerHomeComponent
import info.tommarsh.mynews.presentation.di.HomeComponent
import info.tommarsh.mynews.presentation.di.HomeComponentProvider
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.ActivityArticlesBinding

class ArticlesActivity : AppCompatActivity(), HomeComponentProvider,
    NavController.OnDestinationChangedListener {

    private val binding by lazy { ActivityArticlesBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeComponent().inject(this)
        setContentView(binding.root)
        setUpToolbar()
        setUpNavigation()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.articlesToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setUpNavigation() {
        val controller = findNavController(this, R.id.nav_host_fragment)
        controller.addOnDestinationChangedListener(this)
        setupWithNavController(binding.bottomNavigation, controller)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        binding.articlesToolbarText.text = destination.label
    }

    override fun homeComponent(): HomeComponent {
        return DaggerHomeComponent.factory()
            .create(this, provideCoreComponent())
    }
}
