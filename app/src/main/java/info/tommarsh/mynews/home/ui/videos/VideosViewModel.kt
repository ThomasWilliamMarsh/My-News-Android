package info.tommarsh.mynews.home.ui.videos

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.mynews.core.video.data.VideoRepository
import info.tommarsh.mynews.home.mappers.PlaylistPageMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class VideosViewModel
@Inject internal constructor(
    repository: VideoRepository,
    private val pageMapper: PlaylistPageMapper
) : ViewModel() {

    val videos = repository.getPlaylist()
        .map { pageMapper.map(it) }
}