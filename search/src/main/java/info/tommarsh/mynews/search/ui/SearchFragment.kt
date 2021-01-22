package info.tommarsh.mynews.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.ui.ListLoadStateAdapter
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
    }

    private fun setUpRecyclerView() {
        binding.searchRecyclerView.adapter = setUpAdapter()
    }

    private fun setUpSearchView() {
        binding.searchView.apply {
            isIconified = false
            setIconifiedByDefault(false)
            setOnSearchClickListener {
                onNewQuery(binding.searchView.query.toString())
            }
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
}