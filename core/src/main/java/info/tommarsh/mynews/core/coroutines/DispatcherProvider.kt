package info.tommarsh.mynews.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    fun main() : CoroutineDispatcher

    fun work() : CoroutineDispatcher
}