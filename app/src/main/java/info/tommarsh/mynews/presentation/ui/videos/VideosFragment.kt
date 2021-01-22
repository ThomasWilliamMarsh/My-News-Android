package info.tommarsh.mynews.presentation.ui.videos

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.navigator.ClickDispatcher
import info.tommarsh.mynews.core.navigator.ClickEvent
import info.tommarsh.mynews.core.ui.ListLoadStateAdapter
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.FragmentVideosBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class VideosFragment(private val dispatcher: ClickDispatcher) : Fragment() {

    private lateinit var binding: FragmentVideosBinding

    private val adapter = VideosAdapter()

    private val viewModel by viewModels<VideosViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val concatAdapter = setUpAdapter()
        binding.videosRecyclerView.adapter = concatAdapter
        binding.videosRecyclerView.layoutManager = setLayoutManager(concatAdapter)
        binding.refreshVideo.setOnRefreshListener {
            adapter.refresh()
        }
        binding.refreshVideo.isRefreshing = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.videos.collectLatest { videos ->
                adapter.submitData(videos)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.videos_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> lifecycleScope.launch { dispatcher.dispatch(ClickEvent.Search) }
        }
        return true
    }

    private fun setUpAdapter(): ConcatAdapter {
        return adapter.withLoadStateFooter(
            footer = ListLoadStateAdapter { adapter.retry() }
        ).also {
            adapter.addLoadStateListener { loadState ->
                binding.refreshVideo.isRefreshing =
                    loadState.source.refresh is LoadState.Loading
            }
        }
    }

    private fun setLayoutManager(adapter: ConcatAdapter) =
        GridLayoutManager(context, VIDEOS_SPAN_COUNT).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapter.getItemViewType(position)) {
                        1 -> spanCount
                        else -> 1
                    }
                }
            }
        }

    companion object {
        private const val VIDEOS_SPAN_COUNT = 2
    }
}