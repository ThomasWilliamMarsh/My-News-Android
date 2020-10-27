package info.tommarsh.mynews.core.video.data.remote.source

import info.tommarsh.mynews.core.video.data.remote.model.PlaylistResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface VideoApiService {

    @GET("playlistItems")
    suspend fun getPlaylistItems(@Query("pageToken") page: String?): Response<PlaylistResponse>
}