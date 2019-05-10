package info.tommarsh.presentation.ui.article.videos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.core.network.Outcome
import info.tommarsh.domain.source.VideoRepository
import info.tommarsh.presentation.model.PlaylistItemViewModel
import info.tommarsh.presentation.model.mapper.PlaylistItemViewModelMapper
import info.tommarsh.presentation.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VideosViewModel
@Inject constructor(
    private val repository: VideoRepository,
    private val mapper: PlaylistItemViewModelMapper,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    init {
        refreshVideos()
    }

    private val _videos = MutableLiveData<List<PlaylistItemViewModel>>()

    val videos: LiveData<List<PlaylistItemViewModel>> = _videos

    fun getErrors() = repository.errors

    fun refreshVideos() {
        launch {
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