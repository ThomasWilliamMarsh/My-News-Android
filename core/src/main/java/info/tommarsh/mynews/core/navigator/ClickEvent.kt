package info.tommarsh.mynews.core.navigator

typealias onClickEvent = (ClickEvent) -> Unit

sealed class ClickEvent {

    object Search : ClickEvent()
    object Categories : ClickEvent()
    object OnBoarding : ClickEvent()
    data class Article(
        val webUrl: String,
        val imageUrl: String,
        val title: String,
        val content: String
    ) : ClickEvent()
}
