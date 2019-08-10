package info.tommarsh.categories.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.core.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import info.tommarsh.categories.model.CategoryViewModel
import info.tommarsh.categories.model.MockModelProvider.categoryModel
import info.tommarsh.categories.model.MockModelProvider.categoryViewModel
import info.tommarsh.categories.model.mapper.CategoryDomainToViewModelMapper
import info.tommarsh.categories.ui.CategoryChoiceViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CategoryChoiceViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val categoryRepository = mock<CategoryRepository> {
        on { getSelectedCategoriesStream() }.thenReturn(mock())
        on { getCategories() }.thenReturn(mock())
    }
    private val viewModelMapper = mock<CategoryDomainToViewModelMapper> {
        on { map(listOf(categoryModel)) }.thenReturn(listOf(categoryViewModel))
    }

    private val categoryChoiceViewModel =
        CategoryChoiceViewModel(categoryRepository, viewModelMapper)
    private val categoryObserver = mock<Observer<List<CategoryViewModel>>>()

    @Before
    fun `Before`() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun `Tear Down`() {
        categoryChoiceViewModel.categories.removeObserver(categoryObserver)
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Get Categories`() = runBlockingTest {

        categoryChoiceViewModel.categories.observeForever(categoryObserver)

        verify(categoryRepository).getCategories()
    }

    @Test
    fun `Update Category`() = runBlockingTest {

        categoryChoiceViewModel.updateCategory(categoryViewModel)

        verify(categoryRepository).updateCategory("id", false)

    }
}