package info.tommarsh.presentation.model

data class ArticleViewModel(
    val author: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String
)