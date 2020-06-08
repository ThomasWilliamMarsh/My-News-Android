package info.tommarsh.mynews.onboarding.model

internal sealed class Action {
    data class FetchOnBoardingModel(val key: String) : Action()
    data class SelectedChoice(val key: String, val id: String) : Action()
    object Finished : Action()
}

internal sealed class Event {
    object Loading : Event()
    data class NextScreen(val deepLink: String) : Event()
    data class Fetched(val model: OnBoardingModel) : Event()
    data class Error(val error: Throwable) : Event()
    object Finished: Event()
}