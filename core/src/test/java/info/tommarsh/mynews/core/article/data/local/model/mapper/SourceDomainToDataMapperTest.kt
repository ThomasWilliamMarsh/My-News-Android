package info.tommarsh.mynews.core.article.data.local.model.mapper

import info.tommarsh.mynews.core.MockProvider.source
import info.tommarsh.mynews.core.MockProvider.sourceModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class SourceDomainToDataMapperTest {

    private val mapper =
        SourceDomainToDataMapper()

    @Test
    fun `Map to domain mapper`() {
        val expected = source

        val actual = mapper.map(sourceModel)

        assertEquals(expected, actual)
    }
}