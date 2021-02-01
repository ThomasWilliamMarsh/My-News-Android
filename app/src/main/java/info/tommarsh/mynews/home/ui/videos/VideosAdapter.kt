package info.tommarsh.mynews.home.ui.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.mynews.core.util.createDiffItemCallback
import info.tommarsh.mynews.core.util.launchExternal
import info.tommarsh.mynews.core.util.loadUrl
import info.tommarsh.mynews.home.R
import info.tommarsh.mynews.home.databinding.ItemMainVideoBinding
import info.tommarsh.mynews.home.databinding.ItemVideoBinding
import info.tommarsh.mynews.home.model.PlaylistItemViewModel

internal class VideosAdapter :
    PagingDataAdapter<PlaylistItemViewModel, RecyclerView.ViewHolder>(DIFFER) {

    companion object {
        const val TYPE_PRIMARY_VIDEO = 1
        const val TYPE_SECONDARY_VIDEO = 2
        private val DIFFER = createDiffItemCallback<PlaylistItemViewModel> { old, new ->
            old.videoId == new.videoId
        }
    }

    override fun onBindViewHolder(viewholder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { video ->
            when (viewholder) {
                is PrimaryVideoViewHolder -> viewholder.bind(video)
                is SecondaryVideoViewHolder -> viewholder.bind(video)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_PRIMARY_VIDEO -> {
                val binding =
                    ItemMainVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PrimaryVideoViewHolder(binding)
            }
            TYPE_SECONDARY_VIDEO -> {
                val binding =
                    ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SecondaryVideoViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type for video adapter!")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_PRIMARY_VIDEO
            else -> TYPE_SECONDARY_VIDEO
        }
    }
}

private class PrimaryVideoViewHolder(private val binding: ItemMainVideoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(video: PlaylistItemViewModel) {
        val context = binding.root.context
        binding.mainVideoImage.loadUrl(video.thumbnail)
        binding.mainVideoTitle.text = video.title
        binding.root.setOnClickListener {
            context.launchExternal(context.getString(R.string.youtube_url, video.videoId))
        }
    }
}

internal class SecondaryVideoViewHolder(private val binding: ItemVideoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(video: PlaylistItemViewModel) {
        val context = binding.root.context
        binding.videoImage.loadUrl(video.thumbnail)
        binding.videoTitle.text = video.title
        binding.videoUpdated.text = video.publishedAt
        binding.root.setOnClickListener {
            context.launchExternal(context.getString(R.string.youtube_url, video.videoId))
        }
    }
}