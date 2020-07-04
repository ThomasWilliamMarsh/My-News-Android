package info.tommarsh.mynews.core.article.data.local.model

import info.tommarsh.mynews.core.MockProvider.article
import info.tommarsh.mynews.core.MockProvider.articleModel
import info.tommarsh.mynews.core.MockProvider.source
import info.tommarsh.mynews.core.MockProvider.sourceModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class MappersTest {
    @Test
    fun `Map source domain model to data model`() {
        val expected = source

        val actual = sourceModel.toDataModel()

        assertEquals(expected, actual)
    }

    @Test
    fun `Map source data model to domain model`() {
        val expected = sourceModel

        val actual = source.toDomainModel()

        assertEquals(expected, actual)
    }

    @Test
    fun `Map article data model to domain model`() {
        val expected = article

        val actual = article.toDomainModel()

        assertEquals(expected, actual)
    }

    @Test
    fun `Map article domain model to data model`() {
        val expected = articleModel

        val actual = article.toDomainModel()

        assertEquals(expected, actual)
    }
}