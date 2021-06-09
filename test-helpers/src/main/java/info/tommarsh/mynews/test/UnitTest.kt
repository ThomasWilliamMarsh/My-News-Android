package info.tommarsh.mynews.test

import com.appmattus.kotlinfixture.kotlinFixture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

abstract class UnitTest<T : Any> {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    val fixture = kotlinFixture()

    protected lateinit var sut: T

    abstract fun createSut(): T

    @Before
    fun setUp() {
        sut = createSut()
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }
}