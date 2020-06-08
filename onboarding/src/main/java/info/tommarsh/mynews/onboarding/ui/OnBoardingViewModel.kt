package info.tommarsh.mynews.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.onboarding.data.OnBoardingDataSource
import info.tommarsh.mynews.onboarding.model.Action
import info.tommarsh.mynews.onboarding.model.Event
import info.tommarsh.mynews.onboarding.model.OnBoardingModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
internal class OnBoardingViewModel
@Inject constructor(
    private val dataSource: OnBoardingDataSource,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _events = MutableStateFlow<Event>(Event.Loading)
    val events: StateFlow<Event> get() = _events

    private lateinit var currentScreen: OnBoardingModel

    fun postAction(action: Action) {
        when (action) {
            is Action.FetchOnBoardingModel -> {
                fetchOnBoardingModel(action.key)
            }
            is Action.SelectedChoice -> {
                if(currentScreen.deeplink != null) {
                    _events.value = Event.NextScreen(currentScreen.deeplink!!)
                } else {
                    _events.value = Event.Finished
                }
            }
        }
    }

    private fun fetchOnBoardingModel(key: String) {
        viewModelScope.launch(dispatcherProvider.work()) {
            _events.value = Event.Loading
            _events.value = try {
                currentScreen = dataSource.getOnBoardingModel(key)
                Event.Fetched(currentScreen)
            } catch (throwable: Throwable) {
                Event.Error(throwable)
            }
        }
    }
}