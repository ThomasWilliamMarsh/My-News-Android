package info.tommarsh.mynews.core.article.data.local.source

import info.tommarsh.mynews.core.article.data.local.model.toDataModel
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.test.UnitTest
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ArticlesLocalDataStoreTest : UnitTest<ArticlesLocalDataStore>() {

    private val dao = mock<ArticlesDao>()
    private val category = fixture<String>()

    @Test
    fun `get articles for category`() = runBlocking<Unit> {

        sut.getArticlesForCategory(category)

        verify(dao).getArticlesForCategory(category)
    }

    @Test
    fun `clear categories from db`() = runBlocking {

        sut.clearCategory(category)

        verify(dao).deleteCategory(category)
    }

    @Test
    fun `insert articles to db`() = runBlocking {
        val articleModel = fixture<ArticleModel>()
        val article = articleModel.toDataModel()

        sut.insertArticles(listOf(articleModel))

        verify(dao).insertArticles(article)
    }

    override fun createSut(): ArticlesLocalDataStore {
        return ArticlesLocalDataStore(dao)
    }
}