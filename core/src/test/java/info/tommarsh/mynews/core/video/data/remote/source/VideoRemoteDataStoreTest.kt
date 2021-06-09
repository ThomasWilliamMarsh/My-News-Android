package info.tommarsh.mynews.core.video.data.remote.source

import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Resource
import info.tommarsh.mynews.core.util.ConnectionManager
import info.tommarsh.mynews.core.util.NetworkHelper
import info.tommarsh.mynews.core.video.data.remote.model.PlaylistResponse
import info.tommarsh.mynews.core.video.data.remote.model.toDomainModel
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class VideoRemoteDataStoreTest : UnitTest<VideoRemoteDataStore>() {

    private val playlist = fixture<PlaylistResponse>()
    private val token = fixture<String>()
    private val goodResponse = Response.success(playlist)

    private val connectionManager = mock<ConnectionManager> {
        on { isConnected }.thenReturn(true)
    }
    private val network = NetworkHelper(connectionManager)
    private val api = mock<VideoApiService> {
        onBlocking { getPlaylistItems(page = token) }.thenReturn(goodResponse)
    }

    @Test
    fun `Successfully get Videos from network`() = runBlockingTest {
        val expected = Resource.Data(playlist.toDomainModel())

        val actual = sut.getPlaylist(page = token)

        assertEquals(expected, actual)
    }

    @Test
    fun `Fail to get videos from network`() = runBlockingTest {
        whenever(connectionManager.isConnected).thenReturn(false)

        val resource = sut.getPlaylist(page = token)

        assertTrue(resource is Resource.Error)
        assertTrue((resource as Resource.Error).error is NetworkException.NoInternetException)
    }

    override fun createSut(): VideoRemoteDataStore {
        return VideoRemoteDataStore(api, network)
    }
}