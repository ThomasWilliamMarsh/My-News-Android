package info.tommarsh.mynews.presentation.ui.top

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import info.tommarsh.core.ViewModel
import info.tommarsh.mynews.presentation.ui.headerDelegate
import info.tommarsh.mynews.presentation.ui.primaryArticleDelegate
import info.tommarsh.mynews.presentation.ui.secondaryArticleDelegate
import info.tommarsh.mynews.presentation.ui.videoDelegate
import info.tommarsh.mynews.presentation.util.DelegateDiffCallback

class TopNewsAdapter : AsyncListDifferDelegationAdapter<ViewModel>(DelegateDiffCallback()) {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_PRIMARY_ARTICLE = 1
        const val TYPE_SECONDARY_ARTICLE = 2
        const val TYPE_VIDEO = 3
    }

    init {
        delegatesManager
            .addDelegate(TYPE_HEADER, headerDelegate())
            .addDelegate(TYPE_PRIMARY_ARTICLE, primaryArticleDelegate())
            .addDelegate(TYPE_SECONDARY_ARTICLE, secondaryArticleDelegate())
            .addDelegate(TYPE_VIDEO, videoDelegate())
    }
}