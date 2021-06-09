package info.tommarsh.mynews.onboarding.ui

import android.net.Network
import com.google.android.play.core.splitinstall.SplitInstallManager
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Resource
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.onboarding.data.OnBoardingDataSource
import info.tommarsh.mynews.onboarding.model.Action
import info.tommarsh.mynews.onboarding.model.Choices
import info.tommarsh.mynews.onboarding.model.Event
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class OnBoardingViewModelTest : UnitTest<OnBoardingViewModel>() {

    private val dataSource = mock<OnBoardingDataSource>()
    private val preferences = mock<PreferencesRepository>()
    private val dynamicFeatureManager = mock<SplitInstallManager>()

    @Test
    fun `Send finish event and flag complete on skipping onboarding`() {

        sut.postAction(Action.IntroductionSkipped)

        verify(preferences).flagOnBoardingComplete()

        assertEquals(sut.events.value, Event.Finished)
    }

    @Test
    fun `Save sources, uninstall module, and send finish event when selected sources`() {
        val country = fixture<String>()
        val module = "onboarding"

        sut.postAction(Action.SelectedCountry(country))

        verify(preferences).saveCountry(country)
        verify(preferences).flagOnBoardingComplete()
        verify(dynamicFeatureManager).deferredUninstall(listOf(module))

        assertEquals(sut.events.value, Event.Finished)
    }

    @Test
    fun `Successfully fetch choices`() = runBlockingTest {
        val choices = fixture<Choices>()
        val key = fixture<String>()
        whenever(dataSource.getOnBoardingChoices(key)).thenReturn(Resource.Data(choices))

        sut.postAction(Action.FetchChoices(key))

        verify(dataSource).getOnBoardingChoices(key)

        assertEquals(sut.events.value, Event.Fetched(choices.choices))
    }


    @Test
    fun `Fail to fetch choices`() = runBlockingTest {
        val key = fixture<String>()
        val exception = NetworkException.ServerException()
        whenever(dataSource.getOnBoardingChoices(key)).thenReturn(Resource.Error(exception))

        sut.postAction(Action.FetchChoices(key))

        verify(dataSource).getOnBoardingChoices(key)

        assertEquals(sut.events.value, Event.Error(exception))
    }

    override fun createSut(): OnBoardingViewModel {
        return OnBoardingViewModel(
            dataSource,
            preferences,
            dynamicFeatureManager
        )
    }

}