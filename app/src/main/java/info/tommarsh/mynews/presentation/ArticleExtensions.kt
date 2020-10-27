package info.tommarsh.mynews.presentation

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import info.tommarsh.mynews.presentation.model.ArticleViewModel

import info.tommarsh.presentation.R

fun ArticleViewModel.setClickListenerFor(view: View) {
    view.setOnClickListener {
        val bundle = bundleOf(
            "title" to title,
            "text" to content,
            "url" to urlToImage
        )
        it.findNavController().navigate(R.id.navigation_article, bundle)
    }
}


