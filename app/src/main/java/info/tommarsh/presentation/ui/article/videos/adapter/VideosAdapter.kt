package info.tommarsh.presentation.ui.article.videos.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import info.tommarsh.core.extensions.getDiffUtilItemCallback
import info.tommarsh.presentation.model.PlaylistItemViewModel
import info.tommarsh.presentation.ui.article.videos.adapter.viewholder.PlaylistItemViewholder

class VideosAdapter : ListAdapter<PlaylistItemViewModel, PlaylistItemViewholder>(callback) {

    companion object {
        val callback = getDiffUtilItemCallback<PlaylistItemViewModel> { old, new -> old.videoId == new.videoId }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistItemViewholder {
        return PlaylistItemViewholder(parent)
    }

    override fun onBindViewHolder(holder: PlaylistItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }
}