package info.tommarsh.presentation.ui.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import info.tommarsh.core.extensions.getDiffUtilItemCallback
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.ui.search.adapter.viewholder.ArticleViewHolder

class SearchAdapter : ListAdapter<ArticleViewModel, ArticleViewHolder>(
    callback
) {
    companion object {
        private val callback = getDiffUtilItemCallback<ArticleViewModel> { old, new -> old.url == new.url }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticleViewHolder(parent)

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) = holder.bind(getItem(position))
}