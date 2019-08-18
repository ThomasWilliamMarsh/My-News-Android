package info.tommarsh.mynews.core

sealed class Outcome<T> {
    data class Success<T>(val data: T) : Outcome<T>()
    data class Error<T>(val error: NetworkException) : Outcome<T>()
}