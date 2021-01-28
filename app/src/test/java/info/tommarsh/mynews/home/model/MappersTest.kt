package info.tommarsh.mynews.home.model

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.home.model.MockModelProvider.articleModel
import info.tommarsh.mynews.home.model.MockModelProvider.articleViewModel
import info.tommarsh.mynews.home.model.MockModelProvider.playlistItemModel
import info.tommarsh.mynews.home.model.MockModelProvider.playlistItemViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class MappersTest {

    private val timeHelper = mock<TimeHelper> {
        on { timeBetween(now = any(), isoString = any()) }.thenReturn("1 hour ago")
    }

    @Test
    fun `Map article to ViewModel`() {
        val expected = articleViewModel

        val actual = articleModel.toViewModel(timeHelper)

        assertEquals(expected, actual)
    }

    @Test
    fun `Map playlist to ViewModel`() {
        val expected = playlistItemViewModel

        val actual = playlistItemModel.toViewModel(timeHelper)

        assertEquals(expected, actual)
    }
}