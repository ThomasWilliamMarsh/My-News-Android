package info.tommarsh.mynews.search.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.util.*
import info.tommarsh.mynews.search.model.SearchItemViewModel
import info.tommarsh.mynews.search.ui.adapter.SearchAdapter
import info.tommarsh.search.databinding.ActivitySearchBinding
import kotlinx.android.synthetic.main.activity_search.*

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }

    private val adapter = SearchAdapter()

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(search_toolbar)
        setUpViewModel()
        setUpRecyclerView()
        setUpSearchView()
        onIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        onIntent(intent)
    }

    private fun setUpRecyclerView() {
        binding.searchRecyclerView.adapter = adapter
    }

    private fun setUpViewModel() {
        viewModel.articles.observe(this, Observer(::onSearchResults))
        viewModel.errors.observe(this, Observer(::onError))
    }

    private fun setUpSearchView() {
        val searchManager = service<SearchManager>(SEARCH_SERVICE)
        binding.searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconified = false
            setIconifiedByDefault(false)
        }
    }

    private fun onIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            binding.searchProgress.makeVisible()
            val query = intent.getStringExtra(SearchManager.QUERY)
            viewModel.searchArticles(query)
        }
    }

    private fun onSearchResults(results: List<SearchItemViewModel>) {
        binding.searchProgress.makeGone()
        adapter.submitList(results)
    }

    private fun onError(exception: NetworkException) {
        binding.searchProgress.makeGone()
        binding.root.snack(exception.localizedMessage)
    }
}