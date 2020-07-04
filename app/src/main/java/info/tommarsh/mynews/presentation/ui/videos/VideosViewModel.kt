package info.tommarsh.mynews.presentation.ui.videos

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.core.video.data.VideoRepository
import info.tommarsh.mynews.presentation.model.PlaylistItemViewModel
import info.tommarsh.mynews.presentation.model.toViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideosViewModel
@ViewModelInject constructor(
    private val repository: VideoRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val timeHelper: TimeHelper
) : ViewModel() {

    private val _videos = MutableLiveData<List<PlaylistItemViewModel>>()

    val videos: LiveData<List<PlaylistItemViewModel>> = _videos

    val errors = repository.errors

    fun refreshVideos() {
        viewModelScope.launch {
            when (val outcome = getPlaylist()) {
                is Outcome.Success -> _videos.postValue(outcome.data.items.map { playlist ->
                    playlist.toViewModel(timeHelper)
                })
                is Outcome.Error -> repository.errors.setError(outcome.error)
            }
        }
    }

    private suspend fun getPlaylist() = withContext(dispatcherProvider.work()) {
        repository.getPlaylist()
    }
}