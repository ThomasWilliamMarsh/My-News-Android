package info.tommarsh.mynews.core.article.data.remote.model

import com.google.gson.annotations.SerializedName

data class SourceResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)