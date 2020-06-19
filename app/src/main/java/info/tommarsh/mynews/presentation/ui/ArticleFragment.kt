package info.tommarsh.mynews.presentation.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import info.tommarsh.presentation.R

open class ArticleFragment : Fragment() {

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