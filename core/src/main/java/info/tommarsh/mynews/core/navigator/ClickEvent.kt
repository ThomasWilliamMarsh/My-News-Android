package info.tommarsh.mynews.core.navigator

sealed class ClickEvent {

    object Search : ClickEvent()
    object Categories : ClickEvent()
    object OnBoarding : ClickEvent()
    data class Article(val id: String, val title: String) : ClickEvent()
}
