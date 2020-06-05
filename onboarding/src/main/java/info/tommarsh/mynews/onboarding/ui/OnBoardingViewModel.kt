package info.tommarsh.mynews.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _events = MutableStateFlow<Event>(Event.Loading)
    val events: StateFlow<Event> get() = _events

    fun postAction(action: Action) {
        when (action) {
            is Action.FetchOnBoardingModel -> {
                fetchOnBoardingModel(action.key)
            }
        }
    }

    private fun fetchOnBoardingModel(key: String) {
        viewModelScope.launch(dispatcherProvider.work()) {
            _events.value = Event.Loading
            try {
                Event.Fetched(dataSource.getOnBoardingModel(key))
            } catch (throwable: Throwable) {
                Event.Error(throwable)
            }
        }
    }
}