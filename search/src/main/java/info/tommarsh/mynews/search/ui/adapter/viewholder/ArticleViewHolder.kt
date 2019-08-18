package info.tommarsh.mynews.search.ui.adapter.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.mynews.core.extensions.inflate
import info.tommarsh.mynews.core.extensions.loadUrl
import info.tommarsh.mynews.search.model.SearchItemViewModel
import info.tommarsh.mynews.search.setClickListenerFor
import info.tommarsh.search.R
import kotlinx.android.synthetic.main.item_search_article.view.*

class ArticleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_search_article)) {

    fun bind(article: SearchItemViewModel) = with(itemView) {
        article_image.loadUrl(article.urlToImage)
        article_title.text = article.title
        article_updated.text = article.publishedAt

        article.setClickListenerFor(this)
    }
}