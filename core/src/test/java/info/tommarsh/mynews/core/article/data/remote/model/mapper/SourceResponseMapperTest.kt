package info.tommarsh.mynews.repository.model.remote.mapper

import info.tommarsh.mynews.core.MockProvider.sourceModel
import info.tommarsh.mynews.core.MockProvider.sourceResponse
import info.tommarsh.mynews.core.article.data.remote.model.mapper.SourceResponseMapper
import junit.framework.Assert.assertEquals
import org.junit.Test

class SourceResponseMapperTest {

    private val mapper =
        SourceResponseMapper()

    @Test
    fun `Map to domain mapper`() {
        val expected = sourceModel

        val actual = mapper.map(sourceResponse)

        assertEquals(expected, actual)
    }
}