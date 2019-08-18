package info.tommarsh.mynews.repository.model.remote.mapper

import info.tommarsh.mynews.repository.model.MockProvider.video
import info.tommarsh.mynews.repository.model.MockProvider.videoModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class PlaylistItemMapperTest {
    private val mapper = PlaylistItemMapper()

    @Test
    fun `Map to domain layer`() {
        val expected = videoModel

        val actual = mapper.map(video)

        assertEquals(expected, actual)
    }
}