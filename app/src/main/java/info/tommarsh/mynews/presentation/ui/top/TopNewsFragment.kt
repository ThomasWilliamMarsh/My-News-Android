
package info.tommarsh.mynews.presentation.ui.top

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.ui.ListLoadStateAdapter
import info.tommarsh.mynews.core.util.consume
import info.tommarsh.mynews.presentation.model.ArticleViewModel
import info.tommarsh.mynews.presentation.ui.ArticleFragment
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.FragmentTopNewsBinding
import javax.inject.Inject

@AndroidEntryPoint
class TopNewsFragment : ArticleFragment() {

    private lateinit var binding: FragmentTopNewsBinding

    @Inject
    lateinit var sharedPreferencesRepository: PreferencesRepository

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
        binding.topNewsRetryButton.setOnClickListener { adapter.retry() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.articles.observe(viewLifecycleOwner, Observer(::onArticlesReceived))
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val dayNightItem = menu.findItem(R.id.action_day_night)
        dayNightItem?.setIcon(
            when (sharedPreferencesRepository.getNightMode()) {
                AppCompatDelegate.MODE_NIGHT_YES -> R.drawable.ic_outline_day
                else -> R.drawable.ic_outline_night
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_news_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_day_night -> consume { sharedPreferencesRepository.toggleNightMode() }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpAdapter(): ConcatAdapter {
        return adapter.withLoadStateFooter(
            footer = ListLoadStateAdapter { adapter.retry() }
        ).also {
            adapter.addLoadStateListener { loadState ->
                binding.topNewsRecyclerView.isVisible =
                    loadState.source.refresh is LoadState.NotLoading
                binding.topNewsRefresher.isRefreshing =
                    loadState.source.refresh is LoadState.Loading
                binding.topNewsRetryButton.isVisible = loadState.source.refresh is LoadState.Error
            }
        }
    }

    private fun onArticlesReceived(articles: PagingData<ArticleViewModel>) {

        adapter.submitData(lifecycle, articles)
    }
}