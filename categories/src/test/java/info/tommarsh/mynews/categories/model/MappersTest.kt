package info.tommarsh.mynews.categories.model

import info.tommarsh.mynews.categories.MockModelProvider.categoryModel
import info.tommarsh.mynews.categories.MockModelProvider.categoryViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class MappersTest {

    @Test
    fun `Map to presentation layer`() {
        val expected = categoryViewModel

        val actual = categoryModel.toViewModel()

        assertEquals(expected, actual)
    }
}