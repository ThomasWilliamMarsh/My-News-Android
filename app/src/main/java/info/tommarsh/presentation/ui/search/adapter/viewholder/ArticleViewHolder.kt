package info.tommarsh.presentation.ui.search.adapter.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.core.extensions.inflate
import info.tommarsh.core.extensions.loadUrl
import info.tommarsh.presentation.R
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.setClickListenerFor
import kotlinx.android.synthetic.main.item_search_article.view.*

class ArticleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_search_article)) {

    fun bind(article: ArticleViewModel) = with(itemView) {
        article_image.loadUrl(article.urlToImage)
        article_title.text = article.title
        article_updated.text = article.publishedAt

        article.setClickListenerFor(this)
    }
}