package info.tommarsh.mynews.presentation.ui.top

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.ui.ListLoadStateAdapter
import info.tommarsh.mynews.core.util.consume
import info.tommarsh.mynews.presentation.ui.ArticleFragment
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.FragmentTopNewsBinding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class TopNewsFragment : ArticleFragment() {

    private lateinit var binding: FragmentTopNewsBinding

    private val adapter = TopNewsAdapter()

    private val viewModel by viewModels<TopNewsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topNewsRecyclerView.adapter = setUpAdapter()
        binding.topNewsRefresher.setOnRefreshListener { adapter.refresh() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            viewModel.articles.collectLatest { data ->
                adapter.submitData(data)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_news_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @OptIn(InternalCoroutinesApi::class)
    private fun setUpAdapter(): ConcatAdapter {
        return adapter.withLoadStateFooter(
            footer = ListLoadStateAdapter { adapter.retry() }
        ).also {
            adapter.addLoadStateListener { loadState ->
                Log.v("LOAD STATE TOP NEWS", "REFRESH STATE IS ${loadState.refresh}")
                binding.topNewsRefresher.isRefreshing =
                    loadState.source.refresh is LoadState.Loading


                if(loadState.refresh is LoadState.NotLoading) {
                    binding.topNewsRecyclerView.scrollToPosition(0)
                }

                binding.topNewsRecyclerView.isVisible =
                    loadState.source.refresh is LoadState.NotLoading
            }
        }
    }
}