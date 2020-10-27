package info.tommarsh.mynews.presentation.ui.videos

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.map
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.video.data.VideoRepository
import info.tommarsh.mynews.presentation.model.toViewModel
import kotlinx.coroutines.flow.map

class VideosViewModel
@ViewModelInject constructor(
    repository: VideoRepository,
    private val timeHelper: TimeHelper
) : ViewModel() {

    val videos = repository.getPlaylist()
        .map { data -> data.map { page -> page.toViewModel(timeHelper) } }
}