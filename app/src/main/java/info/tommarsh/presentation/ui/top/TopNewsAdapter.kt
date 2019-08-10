package info.tommarsh.presentation.ui.top

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.core.extensions.getDiffUtilItemCallback
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.ui.viewholders.ArticleViewHolder
import info.tommarsh.presentation.ui.viewholders.MainArticleViewHolder

class TopNewsAdapter : ListAdapter<ArticleViewModel, RecyclerView.ViewHolder>(
    callback
) {
    companion object {
        private val callback = getDiffUtilItemCallback<ArticleViewModel> { old, new -> old.url == new.url }
        private const val TYPE_MAIN_ARTICLE = 0
        private const val TYPE_ARTICLE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_MAIN_ARTICLE -> MainArticleViewHolder(
            parent
        )
        else -> ArticleViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (holder) {
        is MainArticleViewHolder -> holder.bind(getItem(position))
        is ArticleViewHolder -> holder.bind(getItem(position))
        else -> throw IllegalArgumentException("Invalid ViewHolder type.")
    }

    override fun getItemViewType(position: Int) = when (position) {
        0 -> TYPE_MAIN_ARTICLE
        else -> TYPE_ARTICLE
    }
}