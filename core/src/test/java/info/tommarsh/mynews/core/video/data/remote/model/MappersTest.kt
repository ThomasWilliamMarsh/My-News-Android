package info.tommarsh.mynews.core.video.data.remote.model

import info.tommarsh.mynews.core.MockProvider.playlist
import info.tommarsh.mynews.core.MockProvider.playlistModel
import info.tommarsh.mynews.core.MockProvider.video
import info.tommarsh.mynews.core.MockProvider.videoModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class MappersTest {
    @Test
    fun `Map video to domain layer`() {
        val expected = videoModel

        val actual = video.toDomainModel()

        assertEquals(expected, actual)
    }

    @Test
    fun `Map playlist to domain layer`() {
        val expected = playlistModel

        val actual = playlist.toDomainModel()

        assertEquals(expected, actual)
    }
}