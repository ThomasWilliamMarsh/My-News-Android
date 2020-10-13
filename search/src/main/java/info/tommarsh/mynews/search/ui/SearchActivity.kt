package info.tommarsh.mynews.search.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.ui.ListLoadStateAdapter
import info.tommarsh.mynews.core.util.*
import info.tommarsh.mynews.search.ui.adapter.SearchAdapter
import info.tommarsh.search.databinding.ActivitySearchBinding
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }

    private val adapter = SearchAdapter()

    private var searchJob: Job? = null

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(search_toolbar)
        setUpRecyclerView()
        setUpSearchView()
        setUpRetryButton()
        onIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        onIntent(intent)
    }

    private fun setUpRecyclerView() {
        binding.searchRecyclerView.adapter = setUpAdapter()
    }

    private fun setUpSearchView() {
        val searchManager = service<SearchManager>(SEARCH_SERVICE)
        binding.searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconified = false
            setIconifiedByDefault(false)
        }
    }

    private fun setUpRetryButton() {
        binding.searchRetryButton.setOnClickListener { adapter.retry() }
    }

    private fun setUpAdapter(): ConcatAdapter {
        return adapter.withLoadStateFooter(
            footer = ListLoadStateAdapter { adapter.retry() }
        ).also {
            adapter.addLoadStateListener { loadState ->
                binding.searchRecyclerView.isVisible =
                    loadState.source.refresh is LoadState.NotLoading
                binding.searchProgress.isVisible =
                    loadState.source.refresh is LoadState.Loading
                binding.searchRetryButton.isVisible = loadState.source.refresh is LoadState.Error
            }
        }
    }

    private fun onIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                viewModel.searchArticles(query).collect { searchItems ->
                    adapter.submitData(searchItems)
                }
            }
        }
    }
}