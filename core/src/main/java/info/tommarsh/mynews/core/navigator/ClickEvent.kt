package info.tommarsh.mynews.core.navigator

sealed class ClickEvent {

    object Search : ClickEvent()
    object Categories : ClickEvent()
    object OnBoarding : ClickEvent()
    data class Article(
        val webUrl: String,
        val imageUrl: String,
        val title: String
    ) : ClickEvent()
}
