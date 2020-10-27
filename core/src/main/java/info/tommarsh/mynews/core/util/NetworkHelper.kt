package info.tommarsh.mynews.core.util

import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.NetworkException.*
import retrofit2.Response
import javax.inject.Inject

internal class NetworkHelper @Inject constructor(private val connectionManager: ConnectionManager) {

    suspend fun <T> callApi(
        block: suspend () -> Response<T>
    ): T {
        if (!connectionManager.isConnected) throw NoInternetException()
        return try {
            val response = block()
            when {
                response.code() == 426 -> throw MaxLimitException()
                response.code() == 429 -> throw LimitRatedException()
                response.body() == null -> throw NoResponseException()
                !response.isSuccessful -> throw ServerException()
                else -> response.body()!!
            }
        } catch (exception: NetworkException) {
            throw exception
        }
    }
}