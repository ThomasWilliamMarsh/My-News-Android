package info.tommarsh.mynews.core.article.data.local.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.MockProvider.article
import info.tommarsh.mynews.core.MockProvider.articleModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ArticlesLocalDataStoreTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val dao = mock<ArticlesDao>()
    private val localDataStore = ArticlesLocalDataStore(dao)

    @Test
    fun `get breaking news from DB`() {
        whenever(dao.getBreakingArticles()).thenReturn(flowOf())

        localDataStore.getBreakingNews()

        verify(dao).getBreakingArticles()
    }

    @Test
    fun `Save breaking news to DB`() = runBlocking {

        localDataStore.saveArticles(listOf(articleModel, articleModel))

        verify(dao).replaceBreakingArticles(listOf(article, article))
    }
}