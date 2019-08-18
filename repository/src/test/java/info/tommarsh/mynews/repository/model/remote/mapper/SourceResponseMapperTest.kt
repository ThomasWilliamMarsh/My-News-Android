package info.tommarsh.mynews.repository.model.remote.mapper

import info.tommarsh.mynews.repository.model.MockProvider.sourceModel
import info.tommarsh.mynews.repository.model.MockProvider.sourceResponse
import junit.framework.Assert.assertEquals
import org.junit.Test

class SourceResponseMapperTest {

    private val mapper = SourceResponseMapper()

    @Test
    fun `Map to domain mapper`() {
        val expected = sourceModel

        val actual = mapper.map(sourceResponse)

        assertEquals(expected, actual)
    }
}