package info.tommarsh.mynews.home.mappers

import com.appmattus.kotlinfixture.kotlinFixture
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel
import info.tommarsh.mynews.home.model.ArticleViewModel
import info.tommarsh.mynews.home.model.PlaylistItemViewModel
import org.junit.Assert.assertEquals
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
    fun `Map article to ViewModel`() {
        val model = fixture<ArticleModel>()

        val expected = ArticleViewModel(
            model.author,
            model.description,
            timeString,
            model.title,
            model.content,
            model.url,
            model.urlToImage,
            model.category
        )

        val actual = model.toViewModel(timeHelper)

        assertEquals(expected, actual)
    }

    @Test
    fun `Map playlist to ViewModel`() {
        val model = fixture<PlaylistItemModel>()
        val expected = PlaylistItemViewModel(
            videoId = model.videoId,
            title = model.title,
            publishedAt = timeString,
            thumbnail = model.thumbnail
        )

        val actual = model.toViewModel(timeHelper)

        assertEquals(expected, actual)
    }
}