package info.tommarsh.mynews.presentation.ui.videos

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.mynews.core.util.createDiffItemCallback
import info.tommarsh.mynews.core.util.loadUrl
import info.tommarsh.mynews.presentation.model.PlaylistItemViewModel
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.ItemVideoBinding

internal class VideosAdapter :
    PagingDataAdapter<PlaylistItemViewModel, VideoViewHolder>(DIFFER) {

    companion object {
        private val DIFFER = createDiffItemCallback<PlaylistItemViewModel> { old, new ->
            old.videoId == new.videoId
        }
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_video
    }
}

internal class VideoViewHolder(private val binding: ItemVideoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(video: PlaylistItemViewModel) {
        val context = binding.root.context
        binding.videoImage.loadUrl(video.thumbnail)
        binding.videoTitle.text = video.title
        binding.videoUpdated.text = video.publishedAt
        binding.root.setOnClickListener {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(context.getString(R.string.youtube_url, video.videoId))
                )
            )
        }
    }
}