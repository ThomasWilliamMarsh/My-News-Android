package info.tommarsh.presentation.ui.article

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import info.tommarsh.presentation.R
import info.tommarsh.presentation.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_articles.*

class ArticlesActivity : BaseActivity(), NavController.OnDestinationChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        activityGraph.inject(this)
        observeNightMode()
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
