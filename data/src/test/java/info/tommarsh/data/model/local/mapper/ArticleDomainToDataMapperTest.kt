package info.tommarsh.data.model.local.mapper

import com.nhaarman.mockitokotlin2.mock
import info.tommarsh.data.model.MockProvider.article
import info.tommarsh.data.model.MockProvider.articleModel
import info.tommarsh.data.model.MockProvider.source
import info.tommarsh.data.model.MockProvider.sourceModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class ArticleDomainToDataMapperTest {

    private val sourceMapper = mock<SourceDomainToDataMapper> {
        on { map(sourceModel) }.thenReturn(source)
    }
    private val mapper = ArticleDomainToDataMapper(sourceMapper)

    @Test
    fun `Map to domain layer`() {
        val expected = article

        val actual = mapper.map(articleModel)

        assertEquals(expected, actual)
    }
}