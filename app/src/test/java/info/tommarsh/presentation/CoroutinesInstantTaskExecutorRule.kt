package info.tommarsh.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.runner.Description

class CoroutinesInstantTaskExecutorRule : InstantTaskExecutorRule() {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    override fun starting(description: Description) {
        Dispatchers.setMain(mainThreadSurrogate)
        super.starting(description)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}