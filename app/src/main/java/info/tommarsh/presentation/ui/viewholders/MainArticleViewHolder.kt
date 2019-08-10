package info.tommarsh.presentation.ui.viewholders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.core.extensions.inflate
import info.tommarsh.core.extensions.loadUrl
import info.tommarsh.presentation.R
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.setClickListenerFor
import kotlinx.android.synthetic.main.item_main_article.view.*

class MainArticleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_main_article)) {

    fun bind(article: ArticleViewModel) = with(itemView) {
        main_article_image.loadUrl(article.urlToImage)
        main_article_title.text = article.title
        main_article_updated.text = article.publishedAt

        article.setClickListenerFor(this)
    }
}