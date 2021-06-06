package info.tommarsh.mynews.search.ui

import androidx.paging.PagingData
import app.cash.turbine.test
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.search.mappers.SearchItemPageMapper
import info.tommarsh.mynews.search.model.SearchItemViewModel
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SearchViewModelTest : UnitTest<SearchViewModel>() {

    private val pageMapper = mock<SearchItemPageMapper>()

    private val repository = mock<ArticleRepository>()

    private val articleModelList = fixture<List<ArticleModel>>()
    private val articleModelPage = PagingData.from(articleModelList)

    private val searchItems = fixture<List<SearchItemViewModel>>()
    private val searchItemsPage = PagingData.from(searchItems)

    @Test
    fun `Fetches query from repository`() = runBlockingTest {
        whenever(pageMapper.map(articleModelPage)).thenReturn(searchItemsPage)
        whenever(repository.searchArticles("Dogs")).thenReturn(
            flowOf(articleModelPage)
        )

        sut.searchArticles("Dogs").test {
            assertEquals(expectItem(), articleModelPage)
            expectComplete()
        }

        verify(repository).searchArticles("Dogs")
    }

    override fun createSut(): SearchViewModel {
        return SearchViewModel(repository, pageMapper)
    }
}