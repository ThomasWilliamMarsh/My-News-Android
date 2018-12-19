package info.tommarsh.core.network

import info.tommarsh.core.Mapper
import info.tommarsh.core.network.NetworkException.*
import retrofit2.Call
import javax.inject.Inject

class NetworkHelper @Inject constructor(private val connectionManager: ConnectionManager) {

    fun <Data, Domain> callApi(
        call: Call<Data>,
        mapper: Mapper<Data, Domain>
    ): Outcome<Domain> {
        val response = call.execute()
        return when {
            response.body() == null -> Outcome.Error(NoResponseException())
            !response.isSuccessful -> Outcome.Error(ServerException())
            !connectionManager.isConnected -> Outcome.Error(NoInternetException())
            else -> Outcome.Success(mapper.map(response.body()!!))
        }
    }
}