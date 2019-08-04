package info.tommarsh.presentation.ui.common

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import info.tommarsh.core.ViewModelFactory
import info.tommarsh.presentation.NewsApp.Companion.homeGraph
import info.tommarsh.presentation.R
import javax.inject.Inject

open class BaseFragment : Fragment() {

    @Inject
    protected lateinit var factory: ViewModelFactory

    val fragmentGraph by lazy {
        homeGraph(context!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> findNavController().navigate(R.id.navigation_search)
            R.id.action_edit -> findNavController().navigate(R.id.navigation_choice)
        }
        return true
    }
}