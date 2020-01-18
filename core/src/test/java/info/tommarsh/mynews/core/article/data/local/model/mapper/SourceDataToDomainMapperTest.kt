package info.tommarsh.mynews.core.article.data.local.model.mapper

import info.tommarsh.mynews.core.MockProvider.source
import info.tommarsh.mynews.core.MockProvider.sourceModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class SourceDataToDomainMapperTest {

    private val mapper =
        SourceDataToDomainMapper()

    @Test
    fun `Map to domain mapper`() {
        val expected = sourceModel

        val actual = mapper.map(source)

        assertEquals(expected, actual)
    }
}