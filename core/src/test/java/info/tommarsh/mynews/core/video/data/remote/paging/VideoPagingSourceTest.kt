package info.tommarsh.mynews.core.video.data.remote.paging

import androidx.paging.PagingSource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.MockProvider.playlistModel
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.video.data.remote.source.VideoRemoteDataStore
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test

class VideoPagingSourceTest {

    private val remoteDataStore = mock<VideoRemoteDataStore>()

    private val pagingSource = VideoPagingSource(remoteDataStore)

    @Test
    fun `Successfully fetch videos`() = runBlockingTest{
        whenever(remoteDataStore.getPlaylist("token"))
            .thenReturn(Outcome.Success(playlistModel))

        val result = pagingSource.load(PagingSource.LoadParams.Refresh(
            key = "token",
            loadSize = 20,
            placeholdersEnabled = false
        ))

        assertTrue(result is PagingSource.LoadResult.Page)
    }

    @Test
    fun `Fail to fetch videos`() = runBlockingTest {
        whenever(remoteDataStore.getPlaylist("token"))
            .thenReturn(Outcome.Success(playlistModel))

        val result = pagingSource.load(PagingSource.LoadParams.Refresh(
            key = "token",
            loadSize = 20,
            placeholdersEnabled = false
        ))

        assertTrue(result is PagingSource.LoadResult.Page)
    }
}