package info.tommarsh.data.model.local.mapper

import info.tommarsh.data.model.MockProvider.category
import info.tommarsh.data.model.MockProvider.categoryModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class CategoryDomainToDataMapperTest {

    private val mapper = CategoryDomainToDataMapper()

    @Test
    fun `Map to domain layer`() {
        val expected = category

        val actual = mapper.map(categoryModel)

        assertEquals(expected, actual)
    }
}