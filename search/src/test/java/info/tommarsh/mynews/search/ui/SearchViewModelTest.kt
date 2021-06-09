package info.tommarsh.mynews.search.ui

import androidx.paging.PagingData
import app.cash.turbine.test
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.paging.NoPagingCache
import info.tommarsh.mynews.search.mappers.SearchItemPageMapper
import info.tommarsh.mynews.search.model.SearchItemViewModel
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock

class SearchViewModelTest : UnitTest<SearchViewModel>() {

    private val query = fixture<String>()
    private val articleModelList = fixture<List<ArticleModel>>()
    private val articleModelPage = PagingData.from(articleModelList)
    private val searchItems = fixture<List<SearchItemViewModel>>()
    private val searchItemsPage = PagingData.from(searchItems)

    private val pageMapper = mock<SearchItemPageMapper> {
        on { map(articleModelPage) }.thenReturn(searchItemsPage)
    }
    private val repository = mock<ArticleRepository> {
        on { searchArticles(query) }.thenReturn(flowOf(articleModelPage))
    }

    @Test
    fun `Fetches query from repository`() = runBlockingTest {

        sut.searchArticles(query).test {
            assertEquals(expectItem(), searchItemsPage)
            expectComplete()
        }

        verify(repository).searchArticles(query)
    }

    override fun createSut(): SearchViewModel {
        return SearchViewModel(repository, pageMapper, NoPagingCache())
    }
}