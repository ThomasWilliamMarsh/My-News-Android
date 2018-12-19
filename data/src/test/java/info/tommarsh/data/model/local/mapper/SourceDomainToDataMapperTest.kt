package info.tommarsh.data.model.local.mapper

import info.tommarsh.data.model.MockProvider.source
import info.tommarsh.data.model.MockProvider.sourceModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class SourceDomainToDataMapperTest {

    private val mapper = SourceDomainToDataMapper()

    @Test
    fun `Map to domain mapper`() {
        val expected = source

        val actual = mapper.map(sourceModel)

        assertEquals(expected, actual)
    }
}