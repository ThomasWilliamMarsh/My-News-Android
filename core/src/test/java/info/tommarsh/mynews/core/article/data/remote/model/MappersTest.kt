package info.tommarsh.mynews.core.article.data.remote.model

import info.tommarsh.mynews.core.MockProvider.article
import info.tommarsh.mynews.core.MockProvider.articleModel
import info.tommarsh.mynews.core.MockProvider.sourceModel
import info.tommarsh.mynews.core.MockProvider.sourceResponse
import info.tommarsh.mynews.core.article.data.local.model.toDomainModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class MappersTest {

    @Test
    fun `Map data article model to domain model`() {
        val expected = articleModel

        val actual = article.toDomainModel()

        assertEquals(expected, actual)
    }

    @Test
    fun `Map data source model to domain model`() {
        val expected = sourceModel

        val actual = sourceResponse.toDomainModel()

        assertEquals(expected, actual)
    }
}