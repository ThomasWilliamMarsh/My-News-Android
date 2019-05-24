package info.tommarsh.presentation.ui.categories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.domain.source.CategoryRepository
import info.tommarsh.presentation.model.CategoryViewModel
import info.tommarsh.presentation.model.MockModelProvider.categoryModel
import info.tommarsh.presentation.model.MockModelProvider.categoryViewModel
import info.tommarsh.presentation.model.mapper.CategoryDomainToViewModelMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CategoryChoiceViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

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

    @Before
    fun `Set Up`() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun `Tear Down`() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()

        categoryChoiceViewModel.categories.removeObserver(categoryObserver)
    }

    @Test
    fun `Get Categories`() = testCoroutineDispatcher.runBlockingTest {

        categoryChoiceViewModel.categories.observeForever(categoryObserver)

        verify(categoryRepository).getCategories()
    }

    @Test
    fun `Update Category`() = testCoroutineDispatcher.runBlockingTest {

        categoryChoiceViewModel.updateCategory(categoryViewModel)

        verify(categoryRepository).updateCategory("id", false)
    }
}