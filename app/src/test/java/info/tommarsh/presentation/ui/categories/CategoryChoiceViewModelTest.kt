package info.tommarsh.presentation.ui.categories

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.domain.source.CategoryRepository
import info.tommarsh.presentation.CoroutinesInstantTaskExecutorRule
import info.tommarsh.presentation.model.CategoryViewModel
import info.tommarsh.presentation.model.MockModelProvider.categoryModel
import info.tommarsh.presentation.model.MockModelProvider.categoryViewModel
import info.tommarsh.presentation.model.mapper.CategoryDomainToViewModelMapper
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CategoryChoiceViewModelTest {

    @get:Rule
    var rule: TestRule = CoroutinesInstantTaskExecutorRule()

    private val categoryRepository = mock<CategoryRepository> {
        on { getSelectedCategories() }.thenReturn(mock())
        on { getCategories() }.thenReturn(mock())
    }
    private val viewModelMapper = mock<CategoryDomainToViewModelMapper> {
        on { map(listOf(categoryModel)) }.thenReturn(listOf(categoryViewModel))
    }
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val dispatcherProvider = mock<DispatcherProvider> {
        on { main() }.thenReturn(testCoroutineDispatcher)
        on { work() }.thenReturn(testCoroutineDispatcher)
    }
    private val categoryChoiceViewModel =
        CategoryChoiceViewModel(categoryRepository, viewModelMapper, dispatcherProvider)
    private val categoryObserver = mock<Observer<List<CategoryViewModel>>>()

    @After
    fun `Tear Down`() {
        testCoroutineDispatcher.cleanupTestCoroutines()
        categoryChoiceViewModel.categories.removeObserver(categoryObserver)
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