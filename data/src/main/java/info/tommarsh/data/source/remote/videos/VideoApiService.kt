package info.tommarsh.data.source.remote.videos

import info.tommarsh.data.model.remote.PlaylistResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface VideoApiService {

    @GET("playlistItems")
    suspend fun getPlaylistItems(): Response<PlaylistResponse>
}