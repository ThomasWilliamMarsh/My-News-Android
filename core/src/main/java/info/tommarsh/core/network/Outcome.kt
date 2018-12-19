package info.tommarsh.core.network

sealed class Outcome<T> {
    data class Success<T>(val data: T) : Outcome<T>()
    data class Error<T>(val error: NetworkException) : Outcome<T>()
}