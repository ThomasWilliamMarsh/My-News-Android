package info.tommarsh.mynews.search.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.mynews.core.navigator.ClickEvent
import info.tommarsh.mynews.core.navigator.onClickEvent
import info.tommarsh.mynews.core.util.loadUrl
import info.tommarsh.mynews.search.databinding.ItemSearchArticleBinding
import info.tommarsh.mynews.search.model.SearchItemViewModel

class ArticleViewHolder(
    private val binding: ItemSearchArticleBinding,
    private val onClickEvent: onClickEvent
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: SearchItemViewModel) = with(itemView) {
        binding.articleImage.loadUrl(article.urlToImage)
        binding.articleTitle.text = article.title
        binding.articleUpdated.text = article.publishedAt

        binding.root.setOnClickListener {
            onClickEvent(
                ClickEvent.Article(
                    webUrl = article.url,
                    imageUrl = article.urlToImage,
                    title = article.title,
                    content = article.content
                )
            )
        }
    }
}