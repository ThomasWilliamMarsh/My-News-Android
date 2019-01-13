package info.tommarsh.data.model.local.mapper

import info.tommarsh.data.model.MockProvider.category
import info.tommarsh.data.model.MockProvider.categoryModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class CategoryDataToDomainMapperTest {

    private val mapper = CategoryDataToDomainMapper()

    @Test
    fun `Map to domain layer`() {
        val expected = categoryModel

        val actual = mapper.map(category)

        assertEquals(expected, actual)
    }
}