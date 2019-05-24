package info.tommarsh.presentation.model.mapper

import info.tommarsh.presentation.model.MockModelProvider.categoryModel
import info.tommarsh.presentation.model.MockModelProvider.categoryViewModel
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