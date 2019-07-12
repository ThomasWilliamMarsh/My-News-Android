package info.tommarsh.presentation.ui.article.categories.adapter.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.core.extensions.inflate
import info.tommarsh.core.extensions.loadUrl
import info.tommarsh.presentation.R
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.setClickListenerFor
import kotlinx.android.synthetic.main.item_category_article.view.*

class CategoryArticleViewholder(parent: ViewGroup) :
    RecyclerView.ViewHolder(parent.inflate(R.layout.item_category_article)) {

    fun bind(article: ArticleViewModel) = with(itemView) {
        category_article_name.text = article.title
        category_article_updated.text = article.publishedAt
        if (article.urlToImage.isNotEmpty()) {
            category_article_image.loadUrl(article.urlToImage)
        }

        article.setClickListenerFor(this)
    }
}