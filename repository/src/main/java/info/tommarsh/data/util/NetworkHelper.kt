package info.tommarsh.data.util

import info.tommarsh.core.Mapper
import info.tommarsh.core.NetworkException.*
import info.tommarsh.core.Outcome
import retrofit2.Response
import javax.inject.Inject

internal class NetworkHelper @Inject constructor(private val connectionManager: ConnectionManager) {

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