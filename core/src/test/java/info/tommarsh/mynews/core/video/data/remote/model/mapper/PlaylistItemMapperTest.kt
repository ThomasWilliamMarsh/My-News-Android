package info.tommarsh.mynews.core.video.data.remote.model.mapper

import info.tommarsh.mynews.core.MockProvider.video
import info.tommarsh.mynews.core.MockProvider.videoModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class PlaylistItemMapperTest {
    private val mapper =
        PlaylistItemMapper()

    @Test
    fun `Map to domain layer`() {
        val expected = videoModel

        val actual = mapper.map(video)

        assertEquals(expected, actual)
    }
}