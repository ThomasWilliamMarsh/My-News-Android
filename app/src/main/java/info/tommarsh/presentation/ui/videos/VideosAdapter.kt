package info.tommarsh.presentation.ui.videos

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import info.tommarsh.core.ViewModel
import info.tommarsh.presentation.ui.videoDelegate
import info.tommarsh.presentation.util.DelegateDiffCallback

class VideosAdapter : AsyncListDifferDelegationAdapter<ViewModel>(DelegateDiffCallback()) {

    companion object {
        const val TYPE_VIDEO = 0
    }

    init {
        delegatesManager.addDelegate(TYPE_VIDEO, videoDelegate())
    }
}