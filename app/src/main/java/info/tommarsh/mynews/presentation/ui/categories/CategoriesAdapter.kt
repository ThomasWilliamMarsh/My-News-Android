package info.tommarsh.mynews.presentation.ui.categories

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import info.tommarsh.mynews.core.model.ViewModel
import info.tommarsh.mynews.presentation.ui.carouselDelegate
import info.tommarsh.mynews.presentation.ui.headerDelegate
import info.tommarsh.mynews.presentation.util.DelegateDiffCallback

class CategoriesAdapter : AsyncListDifferDelegationAdapter<ViewModel>(DelegateDiffCallback()) {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_CAROUSEL = 1
    }

    init {
        delegatesManager
            .addDelegate(TYPE_HEADER, headerDelegate())
            .addDelegate(TYPE_CAROUSEL, carouselDelegate())
    }
}