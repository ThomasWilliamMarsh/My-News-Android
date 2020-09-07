package info.tommarsh.mynews.search

import android.content.Intent
import android.view.View
import androidx.core.os.bundleOf
import info.tommarsh.mynews.search.model.SearchItemViewModel

private const val ARTICLE_ACTIVITY_CLASS = "info.tommarsh.mynews.article.ui.ArticleActivity"

fun SearchItemViewModel.setClickListenerFor(view: View) {
    view.setOnClickListener {
        val bundle = bundleOf(
            "title" to title,
            "text" to content,
            "url" to urlToImage
        )
        val intent = Intent().setClassName(
            view.context.packageName,
            ARTICLE_ACTIVITY_CLASS
        ).apply {
            putExtras(bundle)
        }
        view.context.startActivity(intent)
    }
}


