package info.tommarsh.data.source.remote.videos

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.core.network.NetworkHelper
import info.tommarsh.core.network.Outcome
import info.tommarsh.data.model.MockProvider.playlistModel
import info.tommarsh.data.model.remote.mapper.PlaylistResponseMapper
import junit.framework.Assert.assertEquals
import org.junit.Test

class VideoRemoteDataStoreTest {
    private val mapper = mock<PlaylistResponseMapper>()
    private val network = mock<NetworkHelper>()
    private val api = mock<VideoApiService>()

    private val remote = VideoRemoteDataStore(api, network, mapper)

    @Test
    fun `Get Videos from network`() {
        whenever(network.callApi(api.getPlaylistItems(), mapper))
            .thenReturn(Outcome.Success(playlistModel))

        val actual = remote.getPlaylist()

        verify(api, times(2)).getPlaylistItems()
        assertEquals(Outcome.Success(playlistModel), actual)
    }
}