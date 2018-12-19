package info.tommarsh.data.model.remote.mapper

import info.tommarsh.data.model.MockProvider.sourceModel
import info.tommarsh.data.model.MockProvider.sourceResponse
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