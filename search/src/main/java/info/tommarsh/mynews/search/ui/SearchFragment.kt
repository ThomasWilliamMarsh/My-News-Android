package info.tommarsh.mynews.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.ui.ListLoadStateAdapter
import info.tommarsh.mynews.core.util.doOnInsets
import info.tommarsh.mynews.search.ui.adapter.SearchAdapter
import info.tommarsh.search.databinding.FragmentSearchBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val adapter = SearchAdapter()

    private var searchJob: Job? = null

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpSearchView()
        setUpRetryButton()
        setUpWindowInsets()
    }

    private fun setUpRecyclerView() {
        binding.searchRecyclerView.adapter = setUpAdapter()
    }

    private fun setUpWindowInsets() {
        binding.root.doOnInsets { systemBarInsets, _ ->
            binding.searchToolbar.updatePadding(top = systemBarInsets.top)
        }
    }

    private fun setUpSearchView() {
        binding.searchView.apply {
            isIconified = false
            setIconifiedByDefault(false)
            doOnSubmit { query -> onNewQuery(query) }
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

    private fun onNewQuery(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchArticles(query).collect { searchItems ->
                adapter.submitData(searchItems)
            }
        }
    }

    private fun SearchView.doOnSubmit(block: (query: String) -> Unit) {
        setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                block(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}