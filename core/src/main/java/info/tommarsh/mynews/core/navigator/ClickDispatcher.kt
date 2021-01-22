package info.tommarsh.mynews.core.navigator

import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@ActivityScoped
class ClickDispatcher @Inject constructor() {

    private val _clicks = MutableSharedFlow<ClickEvent>()
    val clicks = _clicks.asSharedFlow()

    suspend fun dispatch(clickEvent: ClickEvent) {
        _clicks.emit(clickEvent)
    }
}