package info.tommarsh.mynews.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.core.splitinstall.SplitInstallManager
import info.tommarsh.mynews.core.model.Resource
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.onboarding.data.OnBoardingDataSource
import info.tommarsh.mynews.onboarding.model.Action
import info.tommarsh.mynews.onboarding.model.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
internal class OnBoardingViewModel
@Inject constructor(
    private val dataSource: OnBoardingDataSource,
    private val dispatcherProvider: DispatcherProvider,
    private val preferences: PreferencesRepository,
    private val dynamicFeatureManager: SplitInstallManager
) : ViewModel() {

    private val _events = MutableStateFlow<Event>(Event.Loading)
    val events: StateFlow<Event> get() = _events

    fun postAction(action: Action) {
        when (action) {
            is Action.IntroductionSkipped -> {
                preferences.flagOnBoardingComplete()
                uninstallOnBoardingModule()
                _events.value = Event.Finished
            }
            is Action.FetchChoices -> {
                fetchOnBoardingModel(action.key)
            }
            is Action.SelectedCountry -> {
                preferences.saveCountry(action.country)
                preferences.flagOnBoardingComplete()
                uninstallOnBoardingModule()
                _events.value = Event.Finished
            }
        }
    }

    private fun fetchOnBoardingModel(key: String) {
        viewModelScope.launch(dispatcherProvider.work()) {
            _events.value = Event.Loading
            _events.value = when (val resource = dataSource.getOnBoardingChoices(key)) {
                is Resource.Data -> Event.Fetched(resource.data.choices)
                is Resource.Error -> Event.Error(resource.error)
            }
        }
    }

    private fun uninstallOnBoardingModule() {
        dynamicFeatureManager.deferredUninstall(listOf(ONBOARDING_MODULE))
    }

    companion object {
        private const val ONBOARDING_MODULE = "onboarding"
    }
}