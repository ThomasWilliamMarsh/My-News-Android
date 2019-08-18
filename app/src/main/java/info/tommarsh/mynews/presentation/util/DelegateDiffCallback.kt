package info.tommarsh.mynews.presentation.util

import androidx.recyclerview.widget.DiffUtil
import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.core.ViewModel
import info.tommarsh.mynews.presentation.model.ArticleViewModel
import info.tommarsh.mynews.presentation.model.HeaderViewModel
import info.tommarsh.mynews.presentation.model.PlaylistItemViewModel

class DelegateDiffCallback : DiffUtil.ItemCallback<ViewModel>() {
    override fun areItemsTheSame(old: ViewModel, new: ViewModel) = when {
        old::class != new::class -> false
        old is HeaderViewModel -> old.category == (new as HeaderViewModel).category
        old is PlaylistItemViewModel -> old.videoId == (new as PlaylistItemViewModel).videoId
        old is CategoryViewModel -> old.id == (new as CategoryViewModel).id
        old is ArticleViewModel -> old.url == (new as ArticleViewModel).url
        else -> false
    }

    override fun areContentsTheSame(old: ViewModel, new: ViewModel) = old == new
}