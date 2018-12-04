package info.tommarsh.data.source.remote

import info.tommarsh.domain.Outcome
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class NetworkHelper @Inject constructor() {

    suspend fun <Data, Domain> callApi(call: Deferred<Data>, transformation: (Data) -> Domain): Outcome<Domain> {
        return try {
            Outcome.Success(transformation(call.await()))
        } catch (exception: Exception) {
            Outcome.Error(exception)
        }
    }
}