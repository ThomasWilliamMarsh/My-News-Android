package info.tommarsh.mynews.presentation.ui.videos

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.ui.ListLoadStateAdapter
import info.tommarsh.mynews.presentation.ui.ArticleFragment
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.FragmentVideosBinding
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class VideosFragment : ArticleFragment() {

    private lateinit var binding: FragmentVideosBinding

    private val adapter = VideosAdapter()

    private val viewModel by viewModels<VideosViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.videosRecyclerView.adapter = setUpAdapter()
        binding.videosRecyclerView.layoutManager = setLayoutManager()
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

    private fun setLayoutManager() = GridLayoutManager(context, 2)
}