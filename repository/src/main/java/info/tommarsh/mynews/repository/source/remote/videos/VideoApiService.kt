package info.tommarsh.mynews.repository.source.remote.videos

import info.tommarsh.mynews.repository.model.remote.PlaylistResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface VideoApiService {

    @GET("playlistItems")
    suspend fun getPlaylistItems(): Response<PlaylistResponse>
}