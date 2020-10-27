package info.tommarsh.mynews.onboarding.ui

import com.google.android.play.core.splitinstall.SplitInstallManager
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.onboarding.MockProvider.choices
import info.tommarsh.mynews.onboarding.MockProvider.exception
import info.tommarsh.mynews.onboarding.data.OnBoardingDataSource
import info.tommarsh.mynews.onboarding.model.Action
import info.tommarsh.mynews.onboarding.model.Event
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class OnBoardingViewModelTest {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val dataSource = mock<OnBoardingDataSource>()
    private val dispatcher = mock<DispatcherProvider> {
        on { main() }.thenReturn(testCoroutineDispatcher)
        on { work() }.thenReturn(testCoroutineDispatcher)
    }
    private val preferences = mock<PreferencesRepository>()
    private val dynamicFeatureManager = mock<SplitInstallManager>()

    private val viewModel = OnBoardingViewModel(
        dataSource,
        dispatcher,
        preferences,
        dynamicFeatureManager
    )

    @Before
    fun `Set up`() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun `Tear down`() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Send finish event and flag complete on skipping onboarding`() {

        viewModel.postAction(Action.IntroductionSkipped)

        verify(preferences).flagOnBoardingComplete()

        assertEquals(viewModel.events.value, Event.Finished)
    }

    @Test
    fun `Save sources, uninstall module, and send finish event when selected sources`() {

        viewModel.postAction(Action.SelectedCountry("gb"))

        verify(preferences).saveCountry("gb")
        verify(preferences).flagOnBoardingComplete()
        verify(dynamicFeatureManager).deferredUninstall(listOf("onboarding"))

        assertEquals(viewModel.events.value, Event.Finished)
    }

    @Test
    fun `Successfully fetch choices`() = runBlockingTest {
        whenever(dataSource.getOnBoardingChoices("key")).thenReturn(Outcome.Success(choices))

        viewModel.postAction(Action.FetchChoices("key"))

        verify(dataSource).getOnBoardingChoices("key")

        assertEquals(viewModel.events.value, Event.Fetched(choices.choices))
    }


    @Test
    fun `Fail to fetch choices`() = runBlockingTest {
        whenever(dataSource.getOnBoardingChoices("key")).thenReturn(Outcome.Error(exception))

        viewModel.postAction(Action.FetchChoices("key"))

        verify(dataSource).getOnBoardingChoices("key")

        assertEquals(viewModel.events.value, Event.Error(exception))
    }

}