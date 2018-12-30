package info.tommarsh.presentation.ui.article

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import info.tommarsh.core.extensions.snack
import info.tommarsh.presentation.R
import info.tommarsh.presentation.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_articles.*

class ArticlesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        setSupportActionBar(articles_toolbar)

        val controller = findNavController(this, R.id.nav_host_fragment)
        setupWithNavController(bottom_navigation, controller)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.articles_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> startActivity(Intent(this, SearchActivity::class.java))
        }
        return true
    }

    fun setError(message: String) {
        articles_root.snack(message)
    }
}
