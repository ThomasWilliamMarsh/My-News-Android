package info.tommarsh.mynews.search.mappers

import com.appmattus.kotlinfixture.kotlinFixture
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.search.model.SearchItemViewModel
import info.tommarsh.mynews.search.model.toSearchViewModel
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

class MappersTest {

    private val fixture = kotlinFixture()
    private val timeString = fixture<String>()
    private val timeHelper = mock<TimeHelper> {
        on { timeBetween(now = any(), isoString = any()) }.thenReturn(timeString)
    }

    @Test
    fun `Maps article model to search view model`() {
        val articleModel = fixture<ArticleModel>()
        val expected = SearchItemViewModel(
            author = articleModel.author,
            description = articleModel.description,
            publishedAt = timeString,
            title = articleModel.title,
            content = articleModel.content,
            url = articleModel.url,
            urlToImage = articleModel.urlToImage,
            category = articleModel.category
        )

        val actual = articleModel.toSearchViewModel(timeHelper)

        assertEquals(expected, actual)
    }

}