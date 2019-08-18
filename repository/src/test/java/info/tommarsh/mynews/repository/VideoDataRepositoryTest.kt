package info.tommarsh.mynews.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.NetworkException
import info.tommarsh.mynews.core.Outcome
import info.tommarsh.mynews.core.errors.ErrorLiveData
import info.tommarsh.mynews.repository.model.MockProvider.noInternet
import info.tommarsh.mynews.repository.model.MockProvider.playlistModel
import info.tommarsh.mynews.repository.source.remote.videos.VideoRemoteDataStore
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class VideoDataRepositoryTest {

    private val errors = mock<ErrorLiveData>()
    private val remoteDataStore = mock<VideoRemoteDataStore>()
    private val repository = VideoDataRepository(remoteDataStore, errors)

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