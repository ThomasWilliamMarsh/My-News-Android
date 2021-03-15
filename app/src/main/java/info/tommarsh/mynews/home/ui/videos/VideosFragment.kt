package info.tommarsh.mynews.home.ui.videos

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.navigator.ClickEvent
import info.tommarsh.mynews.core.ui.ListLoadStateAdapter
import info.tommarsh.mynews.home.R
import info.tommarsh.mynews.home.databinding.FragmentVideosBinding
import info.tommarsh.mynews.home.ui.NavigationViewModel
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class VideosFragment : Fragment() {

    private lateinit var binding: FragmentVideosBinding

    private val adapter = VideosAdapter()

    private val viewModel by viewModels<VideosViewModel>()

    private val navigationViewModel by activityViewModels<NavigationViewModel>()

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
        binding.videosRecyclerView.itemAnimator = null
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
            R.id.action_search -> navigationViewModel.dispatchClick(ClickEvent.Search)
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
}