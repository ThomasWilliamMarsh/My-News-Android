package info.tommarsh.presentation.ui.common

import androidx.recyclerview.widget.DiffUtil
import info.tommarsh.core.ViewModel
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.model.CategoryViewModel
import info.tommarsh.presentation.model.PlaylistItemViewModel

class HeaderListItemCallback : DiffUtil.ItemCallback<ViewModel>() {
    override fun areItemsTheSame(old: ViewModel, new: ViewModel) = when {
        old::class != new::class -> false
        old is PlaylistItemViewModel -> old.videoId == (new as PlaylistItemViewModel).videoId
        old is CategoryViewModel -> old.id == (new as CategoryViewModel).id
        old is ArticleViewModel -> old.url == (new as ArticleViewModel).url
        else -> false
    }

    override fun areContentsTheSame(old: ViewModel, new: ViewModel) = old == new
}