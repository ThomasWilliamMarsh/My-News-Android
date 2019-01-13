package info.tommarsh.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.core.network.NetworkException
import info.tommarsh.core.network.Outcome
import info.tommarsh.data.model.MockProvider.noInternet
import info.tommarsh.data.model.MockProvider.playlistModel
import info.tommarsh.data.source.remote.videos.VideoRemoteDataStore
import info.tommarsh.domain.model.PlaylistItemModel
import org.junit.Assert.assertEquals
import org.junit.Test

class VideoDataRepositoryTest {

    private val errors = mock<ErrorLiveData>()
    private val remoteDataStore = mock<VideoRemoteDataStore>()
    private val repository = VideoDataRepository(remoteDataStore, errors)

    private val errorObserver = mock<Observer<NetworkException>>()
    private val videosLivedata = MutableLiveData<List<PlaylistItemModel>>()

    @Test
    fun `Get playlist`() {
        whenever(remoteDataStore.getPlaylist()).thenReturn(Outcome.Success(playlistModel))

        val actual = repository.getPlaylist()

        verify(remoteDataStore).getPlaylist()
        assertEquals(Outcome.Success(playlistModel), actual)
    }

    @Test
    fun `Fail to get playlist`() {
        whenever(remoteDataStore.getPlaylist()).thenReturn(Outcome.Error(noInternet))

        val actual = repository.getPlaylist()

        verify(remoteDataStore).getPlaylist()
        assertEquals(Outcome.Error<NetworkException>(noInternet), actual)
    }
}