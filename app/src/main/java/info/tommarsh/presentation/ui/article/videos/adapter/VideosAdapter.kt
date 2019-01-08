package info.tommarsh.presentation.ui.article.videos.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.core.ViewModel
import info.tommarsh.presentation.model.HeaderViewModel
import info.tommarsh.presentation.model.PlaylistItemViewModel
import info.tommarsh.presentation.ui.article.videos.adapter.viewholder.PlaylistItemViewholder
import info.tommarsh.presentation.ui.common.HeaderListItemCallback
import info.tommarsh.presentation.ui.common.HeaderViewholder

class VideosAdapter
    : ListAdapter<ViewModel, RecyclerView.ViewHolder>(HeaderListItemCallback()) {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_VIDEO = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> HeaderViewholder(parent)
            else -> PlaylistItemViewholder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewholder -> holder.bind((getItem(position) as HeaderViewModel))
            is PlaylistItemViewholder -> holder.bind((getItem(position) as PlaylistItemViewModel))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HeaderViewModel -> TYPE_HEADER
            else -> TYPE_VIDEO
        }
    }

    fun submitListWithHeader(header: String, list: MutableList<ViewModel>?) {
        list?.add(0, HeaderViewModel(header))
        submitList(list)
    }
}