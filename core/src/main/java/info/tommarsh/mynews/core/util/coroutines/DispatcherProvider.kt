package info.tommarsh.mynews.core.util.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    fun main() : CoroutineDispatcher

    fun work() : CoroutineDispatcher
}