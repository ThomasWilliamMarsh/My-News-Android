package info.tommarsh.domain

sealed class Outcome<T> {
    data class Success<T>(val data: T) : Outcome<T>()
    data class Error<T>(val throwable: Throwable) : Outcome<T>()
}