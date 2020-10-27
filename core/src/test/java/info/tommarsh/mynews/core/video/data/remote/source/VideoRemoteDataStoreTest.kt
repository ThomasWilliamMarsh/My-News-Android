package info.tommarsh.mynews.core.video.data.remote.source

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.MockProvider.playlist
import info.tommarsh.mynews.core.MockProvider.playlistModel
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.ConnectionManager
import info.tommarsh.mynews.core.util.NetworkHelper
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import retrofit2.Response

class VideoRemoteDataStoreTest {

    private val goodResponse = Response.success(playlist)
    private val connectionManager = mock<ConnectionManager> {
        on { isConnected }.thenReturn(true)
    }
    private val network = NetworkHelper(connectionManager)
    private val api = mock<VideoApiService> {
        onBlocking { getPlaylistItems(page = "token") }.thenReturn(goodResponse)
    }

    private val remote = VideoRemoteDataStore(
        api,
        network
    )

    @Test
    fun `Successfully get Videos from network`() = runBlockingTest {
        val expected = Outcome.Success(playlistModel)

        val actual = remote.getPlaylist("token")

        assertEquals(expected, actual)
    }

    @Test
    fun `Fail to get videos from network`() = runBlockingTest {
        whenever(connectionManager.isConnected).thenReturn(false)

        val outcome = remote.getPlaylist(page = "token")

        assertTrue(outcome is Outcome.Error)
        assertTrue((outcome as Outcome.Error).error is NetworkException.NoInternetException)
    }
}