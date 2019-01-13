package info.tommarsh.presentation.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.core.network.NetworkException
import info.tommarsh.core.network.Outcome
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.model.MockModelProvider
import info.tommarsh.presentation.model.MockModelProvider.articleModel
import info.tommarsh.presentation.model.MockModelProvider.articleViewModel
import info.tommarsh.presentation.model.MockModelProvider.noInternet
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SearchViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val articlesRepository = mock<ArticleRepository>()
    private val mapper = mock<ArticleViewModelMapper> {
        on { map(MockModelProvider.articleModel) }.thenReturn(MockModelProvider.articleViewModel)
    }
    private val searchViewModel = SearchViewModel(articlesRepository, mapper)
    private val observer = mock<Observer<List<ArticleViewModel>>>()
    private val errorObserver = mock<Observer<NetworkException>>()


    @Test
    fun `Get errors`() {

        searchViewModel.getErrors()

        verify(articlesRepository).errors
    }

    @Test
    fun `get search results`() = runBlocking {
        whenever(articlesRepository.searchArticles("1234")).thenReturn(
            Outcome.Success(
                listOf(
                    articleModel,
                    articleModel
                )
            )
        )

        searchViewModel.getArticlesObservable().observeForever(observer)
        searchViewModel.searchArticles("1234")

        verify(articlesRepository).searchArticles("1234")
        verify(observer).onChanged(listOf(articleViewModel, articleViewModel))
    }

    @Test
    fun `show error when failing to get search results`() = runBlocking {
        whenever(articlesRepository.errors).thenReturn(ErrorLiveData())
        whenever(articlesRepository.searchArticles("1234")).thenReturn(Outcome.Error(noInternet))

        searchViewModel.getErrors().observeForever(errorObserver)
        searchViewModel.searchArticles("1234")

        verify(errorObserver).onChanged(noInternet)
    }
}