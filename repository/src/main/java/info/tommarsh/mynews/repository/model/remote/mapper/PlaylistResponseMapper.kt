package info.tommarsh.mynews.repository.model.remote.mapper

import info.tommarsh.mynews.core.Mapper
import info.tommarsh.mynews.core.model.PlaylistModel
import info.tommarsh.mynews.repository.model.remote.PlaylistResponse
import javax.inject.Inject

class PlaylistResponseMapper
@Inject constructor(private val playlistItemMapper: PlaylistItemMapper) :
    Mapper<PlaylistResponse, PlaylistModel> {
    override fun map(from: PlaylistResponse) = PlaylistModel(
        nextPageToken = from.nextPageToken.orEmpty(),
        prevPageToken = from.prevPageToken.orEmpty(),
        items = from.items.map { playlistItemMapper.map(it) }
    )
}