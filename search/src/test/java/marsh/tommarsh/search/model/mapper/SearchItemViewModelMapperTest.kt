package marsh.tommarsh.search.model.mapper

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import info.tommarsh.core.TimeHelper
import marsh.tommarsh.search.model.MockModelProvider.articleModel
import marsh.tommarsh.search.model.MockModelProvider.articleViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchItemViewModelMapperTest {
    private val timeHelper = mock<TimeHelper> {
        on { timeBetween(now = any(), isoString = any()) }.thenReturn("1 hour ago")
    }

    private val mapper = SearchItemViewModelMapper(timeHelper)

    @Test
    fun `Map to Presentation`() {
        val expected = listOf(articleViewModel)

        val actual = mapper.map(listOf(articleModel))

        assertEquals(expected, actual)
    }
}