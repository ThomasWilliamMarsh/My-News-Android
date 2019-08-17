package info.tommarsh.mynews.presentation.model.mapper

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import info.tommarsh.core.TimeHelper
import info.tommarsh.mynews.presentation.model.MockModelProvider.playlistItemModel
import info.tommarsh.mynews.presentation.model.MockModelProvider.playlistItemViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaylistItemViewModelMapperTest {
    private val timeHelper = mock<TimeHelper> {
        on { timeBetween(any(), any()) }.thenReturn("1 hour ago")
    }

    private val mapper = PlaylistItemViewModelMapper(timeHelper)

    @Test
    fun `Map to presentation layer`() {
        val expected = playlistItemViewModel

        val actual = mapper.map(playlistItemModel)

        assertEquals(expected, actual)
    }
}