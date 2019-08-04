package marsh.tommarsh.search.ui.adapter.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.core.extensions.inflate
import info.tommarsh.core.extensions.loadUrl
import kotlinx.android.synthetic.main.item_search_article.view.*
import marsh.tommarsh.search.R
import marsh.tommarsh.search.model.SearchItemViewModel
import marsh.tommarsh.search.setClickListenerFor

class ArticleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_search_article)) {

    fun bind(article: SearchItemViewModel) = with(itemView) {
        article_image.loadUrl(article.urlToImage)
        article_title.text = article.title
        article_updated.text = article.publishedAt

        article.setClickListenerFor(this)
    }
}