package info.tommarsh.mynews.core.video.data.remote.model

import com.appmattus.kotlinfixture.decorator.nullability.AlwaysNullStrategy
import com.appmattus.kotlinfixture.decorator.nullability.NeverNullStrategy
import com.appmattus.kotlinfixture.decorator.nullability.nullabilityStrategy
import com.appmattus.kotlinfixture.kotlinFixture
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel
import info.tommarsh.mynews.core.video.domain.model.PlaylistModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class MappersTest {

    private val fixture = kotlinFixture()

    @Test
    fun `Map video to domain layer`() {
        val video = fixture<PlaylistItemResponse>(configuration = {
            nullabilityStrategy(NeverNullStrategy)
        })
        val expected = PlaylistItemModel(
            videoId = video.contentDetails?.videoId!!,
            title = video.snippet?.title!!,
            publishedAt = video.snippet?.publishedAt!!,
            thumbnail = video.snippet?.thumbnails?.mediumRes?.url!!
        )

        val actual = video.toDomainModel()

        assertEquals(expected, actual)
    }

    @Test
    fun `Map null video to domain layer`() {
        val video = fixture<PlaylistItemResponse>(configuration = {
            nullabilityStrategy(AlwaysNullStrategy)
        })
        val expected = PlaylistItemModel(
            videoId = "",
            title = "",
            publishedAt = "",
            thumbnail = ""
        )

        val actual = video.toDomainModel()

        assertEquals(expected, actual)
    }

    @Test
    fun `Map playlist to domain layer`() {
        val playlistItem = fixture<PlaylistItemResponse>()
        val playlistResponse = fixture<PlaylistResponse>(configuration = {
            nullabilityStrategy(NeverNullStrategy)
        }).copy(
            items = listOf(playlistItem)
        )

        val expected = PlaylistModel(
            nextPageToken = playlistResponse.nextPageToken!!,
            prevPageToken = playlistResponse.prevPageToken!!,
            items = listOf(playlistItem.toDomainModel())
        )

        val actual = playlistResponse.toDomainModel()

        assertEquals(expected, actual)
    }

    @Test
    fun `Map null playlist to domain layer`() {
        val playlistItem = fixture<PlaylistItemResponse>()
        val playlistResponse = fixture<PlaylistResponse>(configuration = {
            nullabilityStrategy(AlwaysNullStrategy)
        }).copy(
            items = listOf(playlistItem)
        )

        val expected = PlaylistModel(
            nextPageToken = "",
            prevPageToken = "",
            items = listOf(playlistItem.toDomainModel())
        )

        val actual = playlistResponse.toDomainModel()

        assertEquals(expected, actual)
    }
}