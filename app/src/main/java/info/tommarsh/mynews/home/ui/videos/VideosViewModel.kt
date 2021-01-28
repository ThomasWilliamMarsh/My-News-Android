package info.tommarsh.mynews.home.ui.videos

import androidx.lifecycle.ViewModel
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.video.data.VideoRepository
import info.tommarsh.mynews.home.model.toViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class VideosViewModel
@Inject constructor(
    repository: VideoRepository,
    private val timeHelper: TimeHelper
) : ViewModel() {

    val videos = repository.getPlaylist()
        .map { data -> data.map { page -> page.toViewModel(timeHelper) } }
}