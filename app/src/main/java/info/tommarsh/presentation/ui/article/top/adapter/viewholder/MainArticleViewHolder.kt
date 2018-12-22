package info.tommarsh.presentation.ui.article.top.adapter.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.core.extensions.ArticleClickListener
import info.tommarsh.core.extensions.inflate
import info.tommarsh.core.extensions.loadUrl
import info.tommarsh.presentation.R
import info.tommarsh.presentation.model.ArticleViewModel
import kotlinx.android.synthetic.main.item_main_article.view.*

class MainArticleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_main_article)) {

    fun bind(article: ArticleViewModel) = with(itemView) {
        main_article_image.loadUrl(article.urlToImage)
        main_article_title.text = article.title
        main_article_updated.text = article.publishedAt
        main_article_author.text = article.author

        setOnClickListener(ArticleClickListener(article.url))
    }
}