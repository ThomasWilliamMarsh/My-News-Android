package info.tommarsh.mynews.core.video.data.remote.paging

import androidx.paging.PagingSource
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Resource
import info.tommarsh.mynews.core.video.data.remote.source.VideoRemoteDataStore
import info.tommarsh.mynews.core.video.domain.model.PlaylistModel
import info.tommarsh.mynews.test.UnitTest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class VideoPagingSourceTest : UnitTest<VideoPagingSource>() {

    private val remoteDataStore = mock<VideoRemoteDataStore>()

    private val token = fixture<String>()
    private val loadSize = fixture<Int>()
    private val placeholdersEnabled = fixture<Boolean>()

    @Test
    fun `Successfully fetch videos`() = runBlockingTest {
        val playlistModel = fixture<PlaylistModel>()
        whenever(remoteDataStore.getPlaylist(token))
            .thenReturn(Resource.Data(playlistModel))

        val result = sut.load(
            PagingSource.LoadParams.Refresh(
                key = token,
                loadSize = loadSize,
                placeholdersEnabled = placeholdersEnabled
            )
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        assertEquals((result as PagingSource.LoadResult.Page).data, playlistModel.items)
    }

    @Test
    fun `Fail to fetch videos`() = runBlockingTest {
        val networkException = NetworkException.ServerException()
        whenever(remoteDataStore.getPlaylist(token))
            .thenReturn(Resource.Error(networkException))

        val result = sut.load(
            PagingSource.LoadParams.Refresh(
                key = token,
                loadSize = loadSize,
                placeholdersEnabled = placeholdersEnabled
            )
        )

        assertTrue(result is PagingSource.LoadResult.Error)
        assertEquals((result as PagingSource.LoadResult.Error).throwable, networkException)
    }

    override fun createSut(): VideoPagingSource {
        return VideoPagingSource(remoteDataStore)
    }
}