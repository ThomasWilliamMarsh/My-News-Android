package info.tommarsh.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    fun main() : CoroutineDispatcher

    fun work() : CoroutineDispatcher
}