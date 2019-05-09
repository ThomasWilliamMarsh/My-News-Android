package info.tommarsh.presentation.ui.article

import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import info.tommarsh.presentation.R
import info.tommarsh.presentation.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_articles.*

class ArticlesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        setSupportActionBar(articles_toolbar)

        val controller = findNavController(this, R.id.nav_host_fragment)
        setupWithNavController(bottom_navigation, controller)
    }
}
