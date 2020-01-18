package info.tommarsh.mynews.core.video.data

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.MockProvider.noInternet
import info.tommarsh.mynews.core.MockProvider.playlistModel
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.ErrorLiveData
import info.tommarsh.mynews.core.video.data.remote.source.VideoRemoteDataStore
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class VideoDataRepositoryTest {

    private val errors = mock<ErrorLiveData>()
    private val remoteDataStore = mock<VideoRemoteDataStore>()
    private val repository =
        VideoDataRepository(remoteDataStore, errors)

    @Test
    fun `Get playlist`() = runBlocking {
        whenever(remoteDataStore.getPlaylist()).thenReturn(Outcome.Success(playlistModel))

        val actual = repository.getPlaylist()

        verify(remoteDataStore).getPlaylist()
        assertEquals(Outcome.Success(playlistModel), actual)
    }

    @Test
    fun `Fail to get playlist`() = runBlocking {
        whenever(remoteDataStore.getPlaylist()).thenReturn(Outcome.Error(noInternet))

        val actual = repository.getPlaylist()

        verify(remoteDataStore).getPlaylist()
        assertEquals(Outcome.Error<NetworkException>(noInternet), actual)
    }
}