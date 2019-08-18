package info.tommarsh.mynews.repository.model.local.mapper

import info.tommarsh.mynews.repository.model.MockProvider.category
import info.tommarsh.mynews.repository.model.MockProvider.categoryModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class CategoryDataToDomainMapperTest {

    private val mapper = CategoryDataToDomainMapper()

    @Test
    fun `Map to domain layer`() {
        val expected = listOf(categoryModel)

        val actual = mapper.map(listOf(category))

        assertEquals(expected, actual)
    }
}