package info.tommarsh.presentation.model.mapper

import info.tommarsh.presentation.model.MockModelProvider.categoryModel
import info.tommarsh.presentation.model.MockModelProvider.categoryViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryViewModelToDomainMapperTest {
    private val mapper = CategoryViewModelToDomainMapper()

    @Test
    fun `Map to domain layer`() {
        val expected = categoryModel

        val actual = mapper.map(categoryViewModel)

        assertEquals(expected, actual)
    }
}