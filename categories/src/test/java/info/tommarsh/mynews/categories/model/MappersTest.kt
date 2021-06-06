package info.tommarsh.mynews.categories.model

import com.appmattus.kotlinfixture.kotlinFixture
import info.tommarsh.mynews.core.category.domain.CategoryModel
import org.junit.Assert.assertEquals
import org.junit.Test

class MappersTest {

    private val fixture = kotlinFixture()

    @Test
    fun `Map to presentation layer`() {
        val categoryModel = fixture<CategoryModel>()
        val expected = CategoryViewModel(
            id = categoryModel.id,
            name = categoryModel.name,
            selected = categoryModel.selected
        )

        val actual = categoryModel.toViewModel()

        assertEquals(expected, actual)
    }
}