package info.tommarsh.mynews.core.article.data.local.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.mynews.core.MockProvider.article
import info.tommarsh.mynews.core.MockProvider.articleModel
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
    fun `get articles for category`() = runBlocking<Unit> {

        localDataStore.getArticlesForCategory("business")

        verify(dao).getArticlesForCategory("business")
    }

    @Test
    fun `clear categories from db`() = runBlocking {

        localDataStore.clearCategory("business")

        verify(dao).deleteCategory("business")
    }

    @Test
    fun `insert articles to db`() = runBlocking {

        localDataStore.insertArticles(listOf(articleModel, articleModel))

        verify(dao).insertArticles(article, article)
    }
}