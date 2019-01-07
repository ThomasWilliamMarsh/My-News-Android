package info.tommarsh.data.source.remote.videos

import info.tommarsh.data.model.remote.PlaylistResponse
import retrofit2.Call
import retrofit2.http.GET

interface VideoApiService {

    @GET("playlistItems")
    fun getPlaylistItems(): Call<PlaylistResponse>
}