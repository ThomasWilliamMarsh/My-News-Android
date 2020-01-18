package info.tommarsh.mynews.repository.model.remote.mapper

import com.nhaarman.mockitokotlin2.mock
import info.tommarsh.mynews.core.MockProvider.articleModel
import info.tommarsh.mynews.core.MockProvider.articlesResponse
import info.tommarsh.mynews.core.MockProvider.sourceModel
import info.tommarsh.mynews.core.MockProvider.sourceResponse
import info.tommarsh.mynews.core.article.data.remote.model.mapper.ArticleResponseMapper
import info.tommarsh.mynews.core.article.data.remote.model.mapper.SourceResponseMapper
import junit.framework.Assert.assertEquals
import org.junit.Test

class ArticleResponseMapperTest {

    private val sourceMapper = mock<SourceResponseMapper> {
        on { map(sourceResponse) }.thenReturn(sourceModel)
    }
    private val mapper =
        ArticleResponseMapper(
            sourceMapper
        )

    @Test
    fun `Map to domain layer`() {
        val expected = listOf(articleModel, articleModel)

        val actual = mapper.map(articlesResponse)

        assertEquals(expected, actual)
    }
}