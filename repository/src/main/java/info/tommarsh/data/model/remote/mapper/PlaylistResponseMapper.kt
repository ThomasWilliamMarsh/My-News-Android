package info.tommarsh.data.model.remote.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.data.model.remote.PlaylistResponse
import info.tommarsh.core.model.PlaylistModel
import javax.inject.Inject

class PlaylistResponseMapper
@Inject constructor(private val playlistItemMapper: PlaylistItemMapper) : Mapper<PlaylistResponse, PlaylistModel> {
    override fun map(from: PlaylistResponse) = PlaylistModel(
        nextPageToken = from.nextPageToken.orEmpty(),
        prevPageToken = from.prevPageToken.orEmpty(),
        items = from.items.map { playlistItemMapper.map(it) }
    )
}