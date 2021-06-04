package info.tommarsh.mynews.home.ui.top

import androidx.paging.PagingData
import app.cash.turbine.test
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.home.mappers.ArticlePageMapper
import info.tommarsh.mynews.home.model.ArticleViewModel
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class TopNewsViewModelTest : UnitTest<TopNewsViewModel>() {

    private val articleModels = fixture<List<ArticleModel>>()
    private val pagedArticleModels = PagingData.from(articleModels)

    private val articleViewModels = fixture<List<ArticleViewModel>>()
    private val pagedArticleViewModels = PagingData.from(articleViewModels)

    private val pageMapper = mock<ArticlePageMapper> {
        on { map(pagedArticleModels) }.thenReturn(pagedArticleViewModels)
    }
    private val repository = mock<ArticleRepository> {
        onBlocking { getArticlesForCategory("general") }.thenReturn(
            flow { emit(pagedArticleModels) }
        )
    }

    @Test
    fun `Get flow of top stories`() = runBlockingTest {

        sut.articles.test {
            assertEquals(expectItem(), pagedArticleViewModels)
            expectComplete()
        }

        verify(repository).getArticlesForCategory("general")
    }

    override fun createSut(): TopNewsViewModel {
        return TopNewsViewModel(repository, pageMapper)
    }
}