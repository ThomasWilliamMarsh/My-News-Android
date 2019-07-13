package info.tommarsh.data.model.local.mapper

import info.tommarsh.data.model.MockProvider.source
import info.tommarsh.data.model.MockProvider.sourceModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class SourceDataToDomainMapperTest {

    private val mapper = SourceDataToDomainMapper()

    @Test
    fun `Map to domain mapper`() {
        val expected = sourceModel

        val actual = mapper.map(source)

        assertEquals(expected, actual)
    }
}