package info.tommarsh.mynews.repository.model.local.mapper

import info.tommarsh.mynews.repository.model.MockProvider.source
import info.tommarsh.mynews.repository.model.MockProvider.sourceModel
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