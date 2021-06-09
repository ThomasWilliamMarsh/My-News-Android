package info.tommarsh.mynews.core.category.data.local.model

import com.appmattus.kotlinfixture.kotlinFixture
import info.tommarsh.mynews.core.category.domain.CategoryModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class MappersTest {

    private val fixture = kotlinFixture()

    @Test
    fun `Map to domain layer`() {
        val category = fixture<Category>()
        val expected = CategoryModel(
            id = category.id,
            name = category.name,
            selected = category.selected
        )

        val actual = category.toDomainModel()

        assertEquals(expected, actual)
    }

    @Test
    fun `Map to data layer`() {
        val categoryModel = fixture<CategoryModel>()
        val expected = Category(
            id = categoryModel.id,
            name = categoryModel.name,
            selected = categoryModel.selected
        )

        val actual = categoryModel.toDataModel()

        assertEquals(expected, actual)
    }
}