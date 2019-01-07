package info.tommarsh.presentation.ui.article.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import info.tommarsh.core.extensions.snack
import info.tommarsh.core.network.NetworkException
import info.tommarsh.presentation.NewsApp.Companion.graph
import info.tommarsh.presentation.R
import info.tommarsh.presentation.ViewModelFactory
import info.tommarsh.presentation.model.PlaylistItemViewModel
import info.tommarsh.presentation.ui.article.videos.adapter.VideosAdapter
import kotlinx.android.synthetic.main.fragment_videos.*
import javax.inject.Inject

class VideosFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val adapter = VideosAdapter()

    private val viewModel: VideosViewModel by lazy {
        ViewModelProviders.of(this, factory).get(VideosViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        graph(context!!).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_videos, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videos_recycler_view.adapter = adapter
        videos_recycler_view.layoutManager = GridLayoutManager(context, 2)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getVideos().observe(viewLifecycleOwner, Observer(::onVideos))
        viewModel.getErrors().observe(viewLifecycleOwner, Observer(::onError))
    }

    private fun onVideos(videos: List<PlaylistItemViewModel>) {
        adapter.submitList(videos)
    }

    private fun onError(error: NetworkException) {
        videos_root.snack(error.localizedMessage)
    }
}