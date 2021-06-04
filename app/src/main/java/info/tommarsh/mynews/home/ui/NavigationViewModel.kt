package info.tommarsh.mynews.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.mynews.core.navigator.ClickEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

typealias onClickEvent = (ClickEvent) -> Unit

class NavigationViewModel : ViewModel() {

    private val _clicks = MutableSharedFlow<ClickEvent>()
    val clicks = _clicks.asSharedFlow()

    fun dispatchClick(clickEvent: ClickEvent) = viewModelScope.launch {
        _clicks.emit(clickEvent)
    }
}