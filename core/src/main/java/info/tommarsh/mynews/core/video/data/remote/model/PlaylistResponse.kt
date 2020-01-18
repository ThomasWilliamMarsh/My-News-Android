package info.tommarsh.mynews.core.video.data.remote.model

import com.google.gson.annotations.SerializedName

data class PlaylistResponse(
    @SerializedName("nextPageToken")
    val nextPageToken: String?,
    @SerializedName("prevPageToken")
    val prevPageToken: String?,
    @SerializedName("items")
    val items: List<PlaylistItemResponse>
)

data class PlaylistItemResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("snippet")
    val snippet: Snippet?,
    @SerializedName("contentDetails")
    val contentDetails: ContentDetails?
)

data class Snippet(
    @SerializedName("channelId")
    val channelId: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("channelTitle")
    val channelTitle: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("playlistId")
    val playlistId: String?,
    @SerializedName("position")
    val position: Int?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("thumbnails")
    val thumbnails: Thumbnails?,
    @SerializedName("title")
    val title: String?
)

data class ContentDetails(
    @SerializedName("videoId")
    val videoId: String?
)

data class Thumbnails(
    @SerializedName("medium")
    val mediumRes: Medium?
)

data class Medium(
    @SerializedName("height")
    val height: Int?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?
)