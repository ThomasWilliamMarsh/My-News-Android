package info.tommarsh.mynews.core.video.data.remote.source

import info.tommarsh.mynews.core.MockProvider.playlist
import info.tommarsh.mynews.core.MockProvider.playlistModel
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Resource
import info.tommarsh.mynews.core.util.ConnectionManager
import info.tommarsh.mynews.core.util.NetworkHelper
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
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
        val expected = Resource.Data(playlistModel)

        val actual = remote.getPlaylist("token")

        assertEquals(expected, actual)
    }

    @Test
    fun `Fail to get videos from network`() = runBlockingTest {
        whenever(connectionManager.isConnected).thenReturn(false)

        val resource = remote.getPlaylist(page = "token")

        assertTrue(resource is Resource.Error)
        assertTrue((resource as Resource.Error).error is NetworkException.NoInternetException)
    }
}