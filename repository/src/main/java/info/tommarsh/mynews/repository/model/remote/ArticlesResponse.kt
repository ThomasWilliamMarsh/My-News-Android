package info.tommarsh.mynews.repository.model.remote

import com.google.gson.annotations.SerializedName

class ArticlesResponse(
    @SerializedName("articles")
    val articles: List<ArticleResponse>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)