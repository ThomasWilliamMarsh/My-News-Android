package info.tommarsh.presentation.ui.categories

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.core.ViewModel
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.model.HeaderViewModel
import info.tommarsh.presentation.ui.viewholders.CategoryArticleViewholder
import info.tommarsh.presentation.util.HeaderListItemCallback
import info.tommarsh.presentation.ui.viewholders.HeaderViewholder

class CategoriesAdapter :
    ListAdapter<ViewModel, RecyclerView.ViewHolder>(HeaderListItemCallback()) {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ARTICLE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> HeaderViewholder(parent)
            else -> CategoryArticleViewholder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewholder -> holder.bind((getItem(position) as HeaderViewModel))
            is CategoryArticleViewholder -> holder.bind((getItem(position) as ArticleViewModel))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HeaderViewModel -> TYPE_HEADER
            else -> TYPE_ARTICLE
        }
    }

    fun submitWithHeaders(list: List<ArticleViewModel>) {
        val result = mutableListOf<ViewModel>(*list.toTypedArray())
        val split = list.groupBy { it.category }
        split.forEach { pair ->
            val vm = HeaderViewModel(pair.key)
            val index = result.indexOfFirst { it is ArticleViewModel && it.category == pair.key }
            result.add(index, vm)
        }

        submitList(result)
    }
}