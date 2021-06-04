package info.tommarsh.mynews.home.ui

import app.cash.turbine.test
import info.tommarsh.mynews.core.navigator.ClickEvent
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class NavigationViewModelTest : UnitTest<NavigationViewModel>() {

    @Test
    fun `relays click events`() = runBlockingTest {
        val articleClickEvent = ClickEvent.Article("link", "image", "title")
        sut.clicks.test {
            sut.dispatchClick(ClickEvent.OnBoarding)
            assertEquals(expectItem(), ClickEvent.OnBoarding)
            sut.dispatchClick(ClickEvent.Search)
            assertEquals(expectItem(), ClickEvent.Search)
            sut.dispatchClick(ClickEvent.Categories)
            assertEquals(expectItem(), ClickEvent.Categories)
            sut.dispatchClick(articleClickEvent)
            assertEquals(expectItem(), articleClickEvent)
            cancelAndIgnoreRemainingEvents()
        }
    }

    override fun createSut(): NavigationViewModel {
        return NavigationViewModel()
    }
}