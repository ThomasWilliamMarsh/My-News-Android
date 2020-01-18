package info.tommarsh.mynews.core.category.data.local.model.mapper

import info.tommarsh.mynews.core.MockProvider.category
import info.tommarsh.mynews.core.MockProvider.categoryModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class CategoryDataToDomainMapperTest {

    private val mapper =
        CategoryDataToDomainMapper()

    @Test
    fun `Map to domain layer`() {
        val expected = listOf(categoryModel)

        val actual = mapper.map(listOf(category))

        assertEquals(expected, actual)
    }
}