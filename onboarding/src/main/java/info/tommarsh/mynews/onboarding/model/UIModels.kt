package info.tommarsh.mynews.onboarding.model

internal sealed class Action {
    data class FetchOnBoardingModel(val key: String) : Action()
}

internal sealed class Event {
    object Loading : Event()
    data class Fetched(val model: OnBoardingModel) : Event()
    data class Error(val error: Throwable) : Event()
}