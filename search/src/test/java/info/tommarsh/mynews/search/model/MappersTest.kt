package info.tommarsh.mynews.search.model

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.search.model.MockModelProvider.articleModel
import info.tommarsh.mynews.search.model.MockModelProvider.articleViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class MappersTest {
    private val timeHelper = mock<TimeHelper> {
        on { timeBetween(now = any(), isoString = any()) }.thenReturn("1 hour ago")
    }

    @Test
    fun `Map to Presentation`() {
        val expected = articleViewModel

        val actual = articleModel.toSearchViewModel(timeHelper)

        assertEquals(expected, actual)
    }
}