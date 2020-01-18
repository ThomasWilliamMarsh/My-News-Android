package info.tommarsh.mynews.core.util

import info.tommarsh.mynews.core.model.Mapper
import info.tommarsh.mynews.core.model.NetworkException.*
import info.tommarsh.mynews.core.model.Outcome
import retrofit2.Response
import javax.inject.Inject

internal class NetworkHelper @Inject constructor(private val connectionManager: ConnectionManager) {

    suspend fun <Data, Domain> callApi(
        mapper: Mapper<Data, Domain>,
        block: suspend () -> Response<Data>
    ): Outcome<Domain> {
        if (!connectionManager.isConnected) return Outcome.Error(NoInternetException())
        return try {
            val response = block()
            when {
                response.body() == null -> Outcome.Error(NoResponseException())
                !response.isSuccessful -> Outcome.Error(ServerException())
                else -> Outcome.Success(mapper.map(response.body()!!))
            }
        } catch (exception: Exception) {
            Outcome.Error(ServerException())
        }
    }
}