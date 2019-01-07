package info.tommarsh.presentation.ui.article.videos.adapter.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.core.extensions.inflate
import info.tommarsh.core.extensions.loadUrl
import info.tommarsh.presentation.R
import info.tommarsh.presentation.model.PlaylistItemViewModel
import kotlinx.android.synthetic.main.item_video.view.*

class PlaylistItemViewholder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_video)) {

    fun bind(playlistItem: PlaylistItemViewModel) = with(itemView) {
        video_image.loadUrl(playlistItem.thumbnail)
        video_title.text = playlistItem.title
        video_updated.text = playlistItem.publishedAt
    }
}