package info.tommarsh.mynews.onboarding.model

sealed class Action {
    data class FetchChoices(val key: String) : Action()
    data class SelectedCountry(val country: String) : Action()
    object IntroductionSkipped : Action()
}

sealed class Event {
    object Loading : Event()
    data class Fetched(val choices: List<Choice>) : Event()
    data class Error(val error: Throwable) : Event()
    object Finished : Event()
}