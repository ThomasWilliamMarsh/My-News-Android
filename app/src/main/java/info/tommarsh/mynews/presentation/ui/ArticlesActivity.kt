package info.tommarsh.mynews.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import info.tommarsh.mynews.core.di.provideCoreComponent
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.presentation.di.DaggerHomeComponent
import info.tommarsh.mynews.presentation.di.HomeComponent
import info.tommarsh.mynews.presentation.di.HomeComponentProvider
import info.tommarsh.presentation.R
import kotlinx.android.synthetic.main.activity_articles.*
import javax.inject.Inject

class ArticlesActivity : AppCompatActivity(), HomeComponentProvider,
    NavController.OnDestinationChangedListener {

    @Inject
    lateinit var sharedPreferencesRepository: PreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeComponent().inject(this)
        setContentView(R.layout.activity_articles)
        setUpToolbar()
        setUpNavigation()
    }

    private fun setUpToolbar() {
        setSupportActionBar(articles_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setUpNavigation() {
        val controller = findNavController(this, R.id.nav_host_fragment)
        controller.addOnDestinationChangedListener(this)
        setupWithNavController(bottom_navigation, controller)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        articles_toolbar_text.text = destination.label
    }

    override fun homeComponent(): HomeComponent {
        return DaggerHomeComponent.factory()
            .create(this, provideCoreComponent())
    }
}
