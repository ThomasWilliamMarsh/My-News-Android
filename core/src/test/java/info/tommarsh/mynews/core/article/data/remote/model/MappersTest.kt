package info.tommarsh.mynews.core.article.data.remote.model

import com.appmattus.kotlinfixture.decorator.nullability.AlwaysNullStrategy
import com.appmattus.kotlinfixture.decorator.nullability.NeverNullStrategy
import com.appmattus.kotlinfixture.decorator.nullability.nullabilityStrategy
import com.appmattus.kotlinfixture.kotlinFixture
import info.tommarsh.mynews.core.article.data.local.model.Article
import info.tommarsh.mynews.core.article.data.local.model.toDomainModel
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.article.domain.model.SourceModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class MappersTest {

    private val fixture = kotlinFixture()

    @Test
    fun `Map data article model to domain model`() {
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
    fun `Map data source model to domain model`() {
        val sourceResponse = fixture<SourceResponse>(configuration = {
            nullabilityStrategy(NeverNullStrategy)
        })
        val expected = SourceModel(
            id = sourceResponse.id!!,
            name = sourceResponse.name!!
        )

        val actual = sourceResponse.toDomainModel()

        assertEquals(expected, actual)
    }

    @Test
    fun `Map null data source model to domain model`() {
        val sourceResponse = fixture<SourceResponse>(configuration = {
            nullabilityStrategy(AlwaysNullStrategy)
        })
        val expected = SourceModel(
            id = "",
            name = ""
        )

        val actual = sourceResponse.toDomainModel()

        assertEquals(expected, actual)
    }
}