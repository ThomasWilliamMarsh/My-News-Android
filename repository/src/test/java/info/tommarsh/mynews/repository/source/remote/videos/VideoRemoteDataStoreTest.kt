package info.tommarsh.mynews.repository.source.remote.videos

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.Outcome
import info.tommarsh.mynews.repository.model.MockProvider.playlistModel
import info.tommarsh.mynews.repository.model.remote.mapper.PlaylistResponseMapper
import info.tommarsh.mynews.repository.util.NetworkHelper
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class VideoRemoteDataStoreTest {
    private val mapper = mock<PlaylistResponseMapper>()
    private val network = mock<NetworkHelper>()
    private val api = mock<VideoApiService>()

    private val remote = VideoRemoteDataStore(api, network, mapper)

    @Test
    fun `Get Videos from network`() = runBlocking {
        whenever(network.callApi(api.getPlaylistItems(), mapper))
            .thenReturn(Outcome.Success(playlistModel))

        val actual = remote.getPlaylist()

        verify(api, times(2)).getPlaylistItems()
        assertEquals(Outcome.Success(playlistModel), actual)
    }
}