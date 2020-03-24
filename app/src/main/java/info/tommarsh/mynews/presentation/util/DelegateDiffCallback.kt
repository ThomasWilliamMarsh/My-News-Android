package info.tommarsh.mynews.presentation.util

import androidx.recyclerview.widget.DiffUtil
import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.core.model.ViewModel
import info.tommarsh.mynews.presentation.model.ArticleViewModel
import info.tommarsh.mynews.presentation.model.HeaderViewModel
import info.tommarsh.mynews.presentation.model.PlaylistItemViewModel

class DelegateDiffCallback : DiffUtil.ItemCallback<ViewModel>() {
    override fun areItemsTheSame(old: ViewModel, new: ViewModel) = old::class.java == new::class.java
    override fun areContentsTheSame(old: ViewModel, new: ViewModel) = old.contentsTheSame(new)
}