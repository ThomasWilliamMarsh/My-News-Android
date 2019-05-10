package info.tommarsh.presentation.ui.common

import androidx.lifecycle.ViewModel
import info.tommarsh.core.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(private val dispatcherProvider: DispatcherProvider) : ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + dispatcherProvider.main()

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}