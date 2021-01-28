package info.tommarsh.mynews.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import info.tommarsh.mynews.core.util.getDiffUtilItemCallback
import info.tommarsh.mynews.search.model.SearchItemViewModel
import info.tommarsh.mynews.search.ui.adapter.viewholder.ArticleViewHolder
import info.tommarsh.mynews.search.databinding.ItemSearchArticleBinding

class SearchAdapter : PagingDataAdapter<SearchItemViewModel, ArticleViewHolder>(
    callback
) {
    companion object {
        private val callback =
            getDiffUtilItemCallback<SearchItemViewModel> { old, new -> old.url == new.url }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            ItemSearchArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) =
        holder.bind(getItem(position)!!)
}