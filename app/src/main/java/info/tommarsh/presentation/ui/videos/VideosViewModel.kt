package info.tommarsh.presentation.ui.videos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.core.Outcome
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.core.repository.VideoRepository
import info.tommarsh.presentation.model.PlaylistItemViewModel
import info.tommarsh.presentation.model.mapper.PlaylistItemViewModelMapper
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VideosViewModel
@Inject constructor(
    private val repository: VideoRepository,
    private val mapper: PlaylistItemViewModelMapper,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _videos = MutableLiveData<List<PlaylistItemViewModel>>()

    val videos: LiveData<List<PlaylistItemViewModel>> = _videos

    val errors = repository.errors

    fun refreshVideos() {
        viewModelScope.launch {
            when (val outcome = getPlaylist()) {
                is Outcome.Success -> _videos.postValue(outcome.data.items.map { mapper.map(it) })
                is Outcome.Error -> repository.errors.setError(outcome.error)
            }
        }
    }

    private suspend fun getPlaylist() = withContext(dispatcherProvider.work()) {
        repository.getPlaylist()
    }
}