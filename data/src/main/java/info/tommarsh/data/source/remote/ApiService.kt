package info.tommarsh.data.source.remote

import info.tommarsh.data.model.remote.RemoteDataModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {
    @GET("url")
    fun getItems(): Deferred<RemoteDataModel>
}