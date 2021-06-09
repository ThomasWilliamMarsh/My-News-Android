package info.tommarsh.mynews.core.article.data.local.model

import com.appmattus.kotlinfixture.kotlinFixture
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.article.domain.model.SourceModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class MappersTest {

    val fixture = kotlinFixture()

    @Test
    fun `Map source domain model to data model`() {
        val sourceModel = fixture<SourceModel>()
        val expected = Source(
            id = sourceModel.id,
            name = sourceModel.name
        )

        val actual = sourceModel.toDataModel()

        assertEquals(expected, actual)
    }

    @Test
    fun `Map source data model to domain model`() {
        val source = fixture<Source>()
        val expected = SourceModel(
            id = source.id,
            name = source.name
        )

        val actual = source.toDomainModel()

        assertEquals(expected, actual)
    }

    @Test
    fun `Map article data model to domain model`() {
        val article = fixture<Article>()
        val expected = ArticleModel(
            author = article.author,
            content = article.content,
            description = article.description,
            publishedAt = article.publishedAt,
            source = article.source.toDomainModel(),
            title = article.title,
            url = article.url,
            urlToImage = article.urlToImage,
            category = article.category
        )

        val actual = article.toDomainModel()

        assertEquals(expected, actual)
    }

    @Test
    fun `Map article domain model to data model`() {
        val articleModel = fixture<ArticleModel>()
        val expected = Article(
            url = articleModel.url,
            author = articleModel.author,
            content = articleModel.content,
            description = articleModel.description,
            publishedAt = articleModel.publishedAt,
            source = articleModel.source.toDataModel(),
            title = articleModel.title,
            urlToImage = articleModel.urlToImage,
            category = articleModel.category
        )

        val actual = articleModel.toDataModel()

        assertEquals(expected, actual)
    }
}