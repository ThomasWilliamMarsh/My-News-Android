package info.tommarsh.presentation.model.mapper

import info.tommarsh.presentation.model.MockModelProvider.categoryModel
import info.tommarsh.presentation.model.MockModelProvider.categoryViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryViewModelMapperTest {

    private val mapper = CategoryDomainToViewModelMapper()

    @Test
    fun `Map to presentation layer`() {
        val expected = categoryViewModel

        val actual = mapper.map(categoryModel)

        assertEquals(expected, actual)
    }
}