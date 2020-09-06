package info.tommarsh.mynews.search.model

internal sealed class Event {
    object Loading : Event()
    data class FetchedResults(val items: List<SearchItemViewModel>) : Event()
    data class Error(val throwable: Throwable) : Event()
}

internal sealed class Action {
    data class Search(val query: String) : Action()
}