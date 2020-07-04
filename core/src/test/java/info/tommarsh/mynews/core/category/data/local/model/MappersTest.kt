package info.tommarsh.mynews.core.category.data.local.model

import info.tommarsh.mynews.core.MockProvider.category
import info.tommarsh.mynews.core.MockProvider.categoryModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class MappersTest {

    @Test
    fun `Map to domain layer`() {
        val expected = categoryModel

        val actual = category.toDomainModel()

        assertEquals(expected, actual)
    }

    @Test
    fun `Map to data layer`() {
        val expected = category

        val actual = categoryModel.toDataModel()

        assertEquals(expected, actual)
    }
}