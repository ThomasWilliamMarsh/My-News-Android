package marsh.tommarsh.search

import android.view.View
import marsh.tommarsh.search.model.SearchItemViewModel

fun SearchItemViewModel.setClickListenerFor(view: View) {
    view.setOnClickListener {
        val bundle = androidx.core.os.bundleOf(
            "title" to title,
            "text" to content,
            "url" to urlToImage
        )
        val intent =
            android.content.Intent().setClassName(view.context.packageName, "marsh.tommarsh.article.ArticleActivity")
                .apply {
                    putExtras(bundle)
                }
        view.context.startActivity(intent)
    }
}


