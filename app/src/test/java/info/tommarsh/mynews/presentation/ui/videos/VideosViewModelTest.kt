package info.tommarsh.mynews.presentation.ui.videos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.ErrorLiveData
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.core.video.data.VideoRepository
import info.tommarsh.mynews.presentation.model.MockModelProvider.noInternet
import info.tommarsh.mynews.presentation.model.MockModelProvider.playlistItemModel
import info.tommarsh.mynews.presentation.model.MockModelProvider.playlistItemViewModel
import info.tommarsh.mynews.presentation.model.MockModelProvider.playlistModel
import info.tommarsh.mynews.presentation.model.PlaylistItemViewModel
import info.tommarsh.mynews.presentation.model.mapper.PlaylistItemViewModelMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VideosViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val errorLiveData = ErrorLiveData()
    private val videosRepository = mock<VideoRepository> {
        on { errors }.thenReturn(errorLiveData)
    }
    private val mapper = mock<PlaylistItemViewModelMapper> {
        on { map(playlistItemModel) }.thenReturn(playlistItemViewModel)
    }
    private val dispatcherProvider = mock<DispatcherProvider> {
        on { main() }.thenReturn(testCoroutineDispatcher)
        on { work() }.thenReturn(testCoroutineDispatcher)
    }
    private val videosViewModel =
        VideosViewModel(videosRepository, mapper, dispatcherProvider)
    private val videosObserver = mock<Observer<List<PlaylistItemViewModel>>>()
    private val errorObserver = mock<Observer<NetworkException>>()

    @Before
    fun `Set up`() {
        Dispatchers.setMain(testCoroutineDispatcher)
        videosViewModel.errors.observeForever(errorObserver)
        videosViewModel.videos.observeForever(videosObserver)
    }

    @After
    fun `Tear down`() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
        videosViewModel.errors.removeObserver(errorObserver)
        videosViewModel.videos.removeObserver(videosObserver)
    }

    @Test
    fun `Get Videos`() = runBlockingTest {
        whenever(videosRepository.getPlaylist()).thenReturn(Outcome.Success(playlistModel))

        videosViewModel.refreshVideos()

        verify(videosObserver).onChanged(listOf(playlistItemViewModel, playlistItemViewModel))
        verify(videosRepository).getPlaylist()
    }

    @Test
    fun `Get Errors`() = runBlockingTest {
        whenever(videosRepository.getPlaylist()).thenReturn(Outcome.Error(noInternet))

        videosViewModel.refreshVideos()

        verify(errorObserver).onChanged(noInternet)
    }
}