package info.tommarsh.mynews.search.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.mynews.core.util.loadUrl
import info.tommarsh.mynews.search.model.SearchItemViewModel
import info.tommarsh.mynews.search.setClickListenerFor
import info.tommarsh.search.databinding.ItemSearchArticleBinding
import kotlinx.android.synthetic.main.item_search_article.view.*

class ArticleViewHolder(private val binding: ItemSearchArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: SearchItemViewModel) = with(itemView) {
        binding.articleImage.loadUrl(article.urlToImage)
        binding.articleTitle.text = article.title
        binding.articleUpdated.text = article.publishedAt

        article.setClickListenerFor(this)
    }
}