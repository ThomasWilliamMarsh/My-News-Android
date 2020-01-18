package info.tommarsh.mynews.presentation

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import info.tommarsh.mynews.core.model.ViewModel
import info.tommarsh.mynews.presentation.model.ArticleViewModel
import info.tommarsh.mynews.presentation.model.HeaderViewModel
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

fun List<ArticleViewModel>.addHeaders(): List<ViewModel> {
    val result = mutableListOf<ViewModel>(*this.toTypedArray())
    val split = groupBy { it.category }
    split.forEach { pair ->
        val vm = HeaderViewModel(pair.key)
        val index = result.indexOfFirst { it is ArticleViewModel && it.category == pair.key }
        result.add(index, vm)
    }
    return result
}


