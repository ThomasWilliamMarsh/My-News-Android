package info.tommarsh.mynews.home.ui.videos

import androidx.paging.PagingData
import app.cash.turbine.test
import info.tommarsh.mynews.core.video.data.VideoRepository
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel
import info.tommarsh.mynews.home.mappers.PlaylistPageMapper
import info.tommarsh.mynews.home.model.PlaylistItemViewModel
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class VideosViewModelTest : UnitTest<VideosViewModel>() {

    private val playlistItemModels = fixture<List<PlaylistItemModel>>()
    private val pagedPlaylistItemModels = PagingData.from(playlistItemModels)

    private val playlistItemViewModels = fixture<List<PlaylistItemViewModel>>()
    private val pagedPlaylistItemViewModels = PagingData.from(playlistItemViewModels)

    private val pageMapper = mock<PlaylistPageMapper> {
        on { map(pagedPlaylistItemModels) }.thenReturn(pagedPlaylistItemViewModels)
    }
    private val repository = mock<VideoRepository> {
        onBlocking { getPlaylist() }.thenReturn(
            flow { emit(pagedPlaylistItemModels) }
        )
    }

    @Test
    fun `Get flow of videos`() = runBlockingTest {

        sut.videos.test {
            assertEquals(expectItem(), pagedPlaylistItemViewModels)
            expectComplete()
        }

        verify(repository).getPlaylist()
    }

    override fun createSut(): VideosViewModel {
        return VideosViewModel(repository, pageMapper)
    }
}