package info.tommarsh.mynews.core.model

sealed class Resource<T> {
    data class Data<T>(val data: T) : Resource<T>()
    data class Error<T>(val error: NetworkException) : Resource<T>()
}