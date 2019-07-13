package info.tommarsh.data.model.remote.mapper

import com.nhaarman.mockitokotlin2.mock
import info.tommarsh.data.model.MockProvider.articleModel
import info.tommarsh.data.model.MockProvider.articlesResponse
import info.tommarsh.data.model.MockProvider.sourceModel
import info.tommarsh.data.model.MockProvider.sourceResponse
import junit.framework.Assert.assertEquals
import org.junit.Test

class ArticleResponseMapperTest {

    private val sourceMapper = mock<SourceResponseMapper> {
        on { map(sourceResponse) }.thenReturn(sourceModel)
    }
    private val mapper = ArticleResponseMapper(sourceMapper)

    @Test
    fun `Map to domain layer`() {
        val expected = listOf(articleModel, articleModel)

        val actual = mapper.map(articlesResponse)

        assertEquals(expected, actual)
    }
}