package info.tommarsh.presentation.ui.article.videos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import info.tommarsh.core.network.Outcome
import info.tommarsh.domain.source.VideoRepository
import info.tommarsh.presentation.model.PlaylistItemViewModel
import info.tommarsh.presentation.model.mapper.PlaylistItemViewModelMapper
import info.tommarsh.presentation.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class VideosViewModel
@Inject constructor(
    private val repository: VideoRepository,
    private val mapper: PlaylistItemViewModelMapper
) : BaseViewModel() {

    init {
        refreshVideos()
    }

    private val videos = MutableLiveData<List<PlaylistItemViewModel>>()

    fun getVideos(): LiveData<List<PlaylistItemViewModel>> = videos

    fun getErrors() = repository.errors

    fun refreshVideos() {
        launch(Dispatchers.IO) {
            val outcome = repository.getPlaylist()
            when (outcome) {
                is Outcome.Success -> videos.postValue(outcome.data.items.map { mapper.map(it) })
                is Outcome.Error -> repository.errors.setError(outcome.error)
            }
        }
    }
}