package info.tommarsh.mynews.core.video.data.remote.model.mapper

import com.nhaarman.mockitokotlin2.mock
import info.tommarsh.mynews.core.MockProvider.playlist
import info.tommarsh.mynews.core.MockProvider.playlistModel
import info.tommarsh.mynews.core.MockProvider.video
import info.tommarsh.mynews.core.MockProvider.videoModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class PlaylistResponseMapperTest {

    private val videoMapper = mock<PlaylistItemMapper> {
        on { map(video) }.thenReturn(videoModel)
    }
    private val mapper =
        PlaylistResponseMapper(videoMapper)

    @Test
    fun `Map to domain layer`() {
        val expected = playlistModel

        val actual = mapper.map(playlist)

        assertEquals(expected, actual)
    }
}