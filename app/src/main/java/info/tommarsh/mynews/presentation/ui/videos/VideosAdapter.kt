package info.tommarsh.mynews.presentation.ui.videos

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import info.tommarsh.mynews.core.ViewModel
import info.tommarsh.mynews.presentation.ui.videoDelegate
import info.tommarsh.mynews.presentation.util.DelegateDiffCallback

class VideosAdapter : AsyncListDifferDelegationAdapter<ViewModel>(DelegateDiffCallback()) {

    companion object {
        const val TYPE_VIDEO = 0
    }

    init {
        delegatesManager.addDelegate(TYPE_VIDEO, videoDelegate())
    }
}