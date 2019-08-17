package info.tommarsh.mynews.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.runner.Description

class CoroutinesInstantTaskExecutorRule : InstantTaskExecutorRule() {

    val testCoroutineDispatcher = TestCoroutineDispatcher()

    override fun starting(description: Description) {
        Dispatchers.setMain(testCoroutineDispatcher)
        super.starting(description)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}