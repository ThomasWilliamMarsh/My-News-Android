package info.tommarsh.core.network

import info.tommarsh.core.Mapper
import info.tommarsh.core.network.NetworkException.*
import retrofit2.Response
import javax.inject.Inject

class NetworkHelper @Inject constructor(private val connectionManager: ConnectionManager) {

    fun <Data, Domain> callApi(
        response: Response<Data>,
        mapper: Mapper<Data, Domain>
    ): Outcome<Domain> {
        return try {
            when {
                response.body() == null -> Outcome.Error(NoResponseException())
                !response.isSuccessful -> Outcome.Error(ServerException())
                !connectionManager.isConnected -> Outcome.Error(NoInternetException())
                else -> Outcome.Success(mapper.map(response.body()!!))
            }
        } catch (exception: Exception) {
            Outcome.Error(ServerException())
        }
    }
}