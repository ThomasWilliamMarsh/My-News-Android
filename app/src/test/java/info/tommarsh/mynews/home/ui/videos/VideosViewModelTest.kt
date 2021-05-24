package info.tommarsh.mynews.home.ui.videos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.video.data.VideoRepository
import info.tommarsh.mynews.home.model.MockModelProvider.playlistItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class VideosViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val repository = mock<VideoRepository> {
        onBlocking { getPlaylist() }.thenReturn(
            flow {
                emit(PagingData.from(listOf(playlistItemModel)))
            }
        )
    }

    private val timeHelper = mock<TimeHelper>()

    private val viewModel = VideosViewModel(repository, timeHelper)

    @Before
    fun `Set up`() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun `Tear down`() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Get flow of videos`() = testCoroutineDispatcher.runBlockingTest {

        viewModel.videos.collect()

        verify(repository).getPlaylist()
    }
}