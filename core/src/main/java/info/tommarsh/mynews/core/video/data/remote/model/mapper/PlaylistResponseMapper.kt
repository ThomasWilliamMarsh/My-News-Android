package info.tommarsh.mynews.core.video.data.remote.model.mapper

import info.tommarsh.mynews.core.video.data.remote.model.PlaylistResponse
import info.tommarsh.mynews.core.video.domain.model.PlaylistModel
import javax.inject.Inject

class PlaylistResponseMapper
@Inject constructor(private val playlistItemMapper: PlaylistItemMapper) :
    Mapper<PlaylistResponse, PlaylistModel> {
    override fun map(from: PlaylistResponse) =
        PlaylistModel(
            nextPageToken = from.nextPageToken.orEmpty(),
            prevPageToken = from.prevPageToken.orEmpty(),
            items = from.items.map { playlistItemMapper.map(it) }
        )
}