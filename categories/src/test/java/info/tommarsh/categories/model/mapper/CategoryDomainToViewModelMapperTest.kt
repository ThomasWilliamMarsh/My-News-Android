package info.tommarsh.categories.model.mapper

import info.tommarsh.categories.model.mapper.CategoryDomainToViewModelMapper
import info.tommarsh.categories.model.MockModelProvider.categoryModel
import info.tommarsh.categories.model.MockModelProvider.categoryViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryDomainToViewModelMapperTest {

    private val mapper = CategoryDomainToViewModelMapper()

    @Test
    fun `Map to presentation layer`() {
        val expected = listOf(categoryViewModel)

        val actual = mapper.map(listOf(categoryModel))

        assertEquals(expected, actual)
    }
}