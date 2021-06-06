package info.tommarsh.mynews.categories.ui

import app.cash.turbine.test
import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class CategoryChoiceViewModelTest : UnitTest<CategoryChoiceViewModel>() {

    private val categoryModel = fixture<CategoryModel>()
    private val categoryViewModel = CategoryViewModel(
        id = categoryModel.id,
        name = categoryModel.name,
        selected = categoryModel.selected
    )


    private val categoryRepository = mock<CategoryRepository> {
        on { getCategories() }.thenReturn(flowOf(listOf(categoryModel)))
    }

    @Test
    fun `Get Categories`() = runBlockingTest {

        sut.categories.test {
            assertEquals(expectItem(), listOf(categoryViewModel))
            expectComplete()
        }

        verify(categoryRepository).getCategories()
    }

    @Test
    fun `Update Category`() = runBlockingTest {

        sut.updateCategory(categoryViewModel)

        verify(categoryRepository).updateCategory(categoryViewModel.id, categoryViewModel.selected)
    }

    override fun createSut(): CategoryChoiceViewModel {
        return CategoryChoiceViewModel(categoryRepository)
    }
}