package info.tommarsh.mynews.presentation.ui.categories

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import info.tommarsh.core.ViewModel
import info.tommarsh.mynews.presentation.ui.categoryArticleDelegate
import info.tommarsh.mynews.presentation.ui.headerDelegate
import info.tommarsh.mynews.presentation.util.DelegateDiffCallback

class CategoriesAdapter : AsyncListDifferDelegationAdapter<ViewModel>(DelegateDiffCallback()) {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ARTICLE = 1
    }

    init {
        delegatesManager
            .addDelegate(TYPE_HEADER, headerDelegate())
            .addDelegate(TYPE_ARTICLE, categoryArticleDelegate())
    }
}