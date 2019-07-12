package info.tommarsh.presentation

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import info.tommarsh.presentation.model.ArticleViewModel

fun ArticleViewModel.setClickListenerFor(view: View) {
    view.setOnClickListener {
        it.findNavController()
            .navigate(R.id.navigation_article, bundleOf("text" to content, "url" to urlToImage))
    }
}
