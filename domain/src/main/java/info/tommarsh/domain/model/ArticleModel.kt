package info.tommarsh.domain.model


data class ArticleModel(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceModel,
    val title: String,
    val url: String,
    val urlToImage: String
)