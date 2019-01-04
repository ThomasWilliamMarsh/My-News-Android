package info.tommarsh.presentation.ui.article.categories.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.core.ViewModel
import info.tommarsh.core.extensions.getDiffUtilItemCallback
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.model.CategoryHeaderViewModel
import info.tommarsh.presentation.ui.article.categories.adapter.viewholder.CategoryArticleViewholder
import info.tommarsh.presentation.ui.article.categories.adapter.viewholder.CategoryHeaderViewholder

class CategoriesAdapter :
    ListAdapter<ViewModel, RecyclerView.ViewHolder>(callback) {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ARTICLE = 1

        val callback = getDiffUtilItemCallback<ViewModel> { old, new ->
            when {
                old::class != new::class -> false
                old is CategoryHeaderViewModel -> old.category == (new as CategoryHeaderViewModel).category
                old is ArticleViewModel -> old.url == (new as ArticleViewModel).url
                else -> false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> CategoryHeaderViewholder(parent)
            else -> CategoryArticleViewholder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryHeaderViewholder -> holder.bind((getItem(position) as CategoryHeaderViewModel))
            is CategoryArticleViewholder -> holder.bind((getItem(position) as ArticleViewModel))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CategoryHeaderViewModel -> TYPE_HEADER
            else -> TYPE_ARTICLE
        }
    }
}