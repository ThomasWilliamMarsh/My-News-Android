package info.tommarsh.data.model.local.mapper

import com.nhaarman.mockitokotlin2.mock
import info.tommarsh.data.model.MockProvider.article
import info.tommarsh.data.model.MockProvider.articleModel
import info.tommarsh.data.model.MockProvider.source
import info.tommarsh.data.model.MockProvider.sourceModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class ArticleDataToDomainMapperTest {

    private val sourceMapper = mock<SourceDataToDomainMapper> {
        on { map(source) }.thenReturn(sourceModel)
    }
    private val mapper = ArticleDataToDomainMapper(sourceMapper)

    @Test
    fun `Map to domain layer`() {
        val expected = listOf(articleModel)

        val actual = mapper.map(listOf(article))

        assertEquals(expected, actual)
    }
}