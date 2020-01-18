package info.tommarsh.mynews.core.video.data.remote.source

import com.nhaarman.mockitokotlin2.mock
import info.tommarsh.mynews.core.MockProvider.playlist
import info.tommarsh.mynews.core.MockProvider.playlistModel
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.ConnectionManager
import info.tommarsh.mynews.core.util.NetworkHelper
import info.tommarsh.mynews.core.video.data.remote.model.mapper.PlaylistItemMapper
import info.tommarsh.mynews.core.video.data.remote.model.mapper.PlaylistResponseMapper
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import retrofit2.Response

class VideoRemoteDataStoreTest {

    private val goodResponse = Response.success(playlist)
    private val connectionManager = mock<ConnectionManager> {
        on { isConnected }.thenReturn(true)
    }
    private val mapper =
        PlaylistResponseMapper(PlaylistItemMapper())
    private val network = NetworkHelper(connectionManager)
    private val api = mock<VideoApiService> {
        onBlocking { getPlaylistItems() }.thenReturn(goodResponse)
    }

    private val remote = VideoRemoteDataStore(
        api,
        network,
        mapper
    )

    @Test
    fun `Get Videos from network`() = runBlockingTest {

        val expected = Outcome.Success(playlistModel)

        val actual = remote.getPlaylist()

        assertEquals(expected, actual)
    }
}