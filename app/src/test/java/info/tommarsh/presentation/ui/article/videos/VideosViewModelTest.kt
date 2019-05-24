package info.tommarsh.presentation.ui.article.videos

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.core.network.NetworkException
import info.tommarsh.core.network.Outcome
import info.tommarsh.domain.source.VideoRepository
import info.tommarsh.presentation.CoroutinesInstantTaskExecutorRule
import info.tommarsh.presentation.model.MockModelProvider.noInternet
import info.tommarsh.presentation.model.MockModelProvider.playlistItemModel
import info.tommarsh.presentation.model.MockModelProvider.playlistItemViewModel
import info.tommarsh.presentation.model.MockModelProvider.playlistModel
import info.tommarsh.presentation.model.PlaylistItemViewModel
import info.tommarsh.presentation.model.mapper.PlaylistItemViewModelMapper
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class VideosViewModelTest {
    @get:Rule
    var rule: TestRule = CoroutinesInstantTaskExecutorRule()

    private val errorLiveData = ErrorLiveData()
    private val videosRepository = mock<VideoRepository> {
        on { errors }.thenReturn(errorLiveData)
    }
    private val mapper = mock<PlaylistItemViewModelMapper> {
        on { map(playlistItemModel) }.thenReturn(playlistItemViewModel)
    }
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val dispatcherProvider = mock<DispatcherProvider> {
        on { main() }.thenReturn(testCoroutineDispatcher)
        on { work() }.thenReturn(testCoroutineDispatcher)
    }
    private val videosViewModel = VideosViewModel(videosRepository, mapper, dispatcherProvider)
    private val videosObserver = mock<Observer<List<PlaylistItemViewModel>>>()
    private val errorObserver = mock<Observer<NetworkException>>()

    @Before
    fun `Set up`() {
        videosViewModel.errors.observeForever(errorObserver)
        videosViewModel.videos.observeForever(videosObserver)
    }

    @After
    fun `Tear down`() {
        testCoroutineDispatcher.cleanupTestCoroutines()
        videosViewModel.errors.removeObserver(errorObserver)
        videosViewModel.videos.removeObserver(videosObserver)
    }

    @Test
    fun `Get Videos`() = runBlockingTest {
        whenever(videosRepository.getPlaylist()).thenReturn(Outcome.Success(playlistModel))

        videosViewModel.refreshVideos()

        verify(videosObserver).onChanged(listOf(playlistItemViewModel, playlistItemViewModel))
        verify(videosRepository, times(2)).getPlaylist()
    }

    @Test
    fun `Get Errors`() = runBlockingTest {
        whenever(videosRepository.getPlaylist()).thenReturn(Outcome.Error(noInternet))

        videosViewModel.refreshVideos()

        verify(errorObserver).onChanged(noInternet)
    }
}