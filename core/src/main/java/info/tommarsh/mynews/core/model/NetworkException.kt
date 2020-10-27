package info.tommarsh.mynews.core.model

sealed class NetworkException(message: String) : Exception(message) {
    class NoResponseException : NetworkException("No Response from sever")
    class NoInternetException : NetworkException("It seems you have no internet")
    class ServerException : NetworkException("Error with the server")
    class MaxLimitException : NetworkException("Max results reached")
    class LimitRatedException : NetworkException("Naughty. You've been limit rated.")
}