package info.tommarsh.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import info.tommarsh.core.extensions.observeNightMode
import info.tommarsh.core.repository.PreferencesRepository
import info.tommarsh.presentation.NewsApp
import info.tommarsh.presentation.R
import kotlinx.android.synthetic.main.activity_articles.*
import javax.inject.Inject

class ArticlesActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    @Inject
    lateinit var sharedPreferencesRepository: PreferencesRepository

    private val activityGraph by lazy {
        NewsApp.homeGraph(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        activityGraph.inject(this)
        observeNightMode(sharedPreferencesRepository)
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

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        articles_toolbar_text.text = destination.label
    }
}
