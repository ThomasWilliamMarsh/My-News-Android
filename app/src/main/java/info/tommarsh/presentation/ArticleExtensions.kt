package info.tommarsh.presentation

import android.content.Intent
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.ui.article.ArticlesActivity
import info.tommarsh.presentation.ui.search.SearchActivity

fun ArticleViewModel.setClickListenerFor(view: View) {
    view.setOnClickListener {
        val bundle = bundleOf(
            "title" to title,
            "text" to content,
            "url" to urlToImage
        )

        //TODO: Whilst search activity is not within a nav host.
        try {
            it.findNavController().navigate(R.id.navigation_article, bundle)
        } catch (e: IllegalStateException) {
            val intent = Intent().setClassName(view.context.packageName, "marsh.tommarsh.article.ArticleActivity").apply {
                putExtras(bundle)
            }
            view.context.startActivity(intent)
        }
    }
}


