package info.tommarsh.mynews

import androidx.paging.PagingData
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.article.domain.model.SourceModel
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel
import kotlinx.coroutines.flow.flowOf
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

val videoModel =
    PlaylistItemModel(
        "videoId1",
        "Video title 1",
        create1HourAgoTime(),
        "https://edit.co.uk/uploads/2016/12/Image-1-Alternatives-to-stock-photography-Thinkstock.jpg"
    )

val videoModel2 =
    PlaylistItemModel(
        "videoId2",
        "Video title 2",
        create1HourAgoTime(),
        "https://cdn.theatlantic.com/static/mt/assets/science/cat_caviar.jpg"
    )

val article1 =
    ArticleModel(
        "Author1",
        "Content1",
        "Description 1",
        create1HourAgoTime(),
        SourceModel("sourceId1", "sourceName1"),
        "title1",
        "url1",
        "https://cdn.shopify.com/s/files/1/1492/1076/products/Traditional_Black_and_White_Football_Ball_32_Panel_Classic_1400x.jpg?v=1563121775",
        "sport"
    )

val article2 =
    ArticleModel(
        "Author2",
        "Content2",
        "Description 2",
        create1HourAgoTime(),
        SourceModel("sourceId2", "sourceName2"),
        "title2",
        "url2",
        "https://cdn.shopify.com/s/files/1/1492/1076/products/Traditional_Black_and_White_Football_Ball_32_Panel_Classic_1400x.jpg?v=1563121775",
        "sport"
    )

val articlesResponse = flowOf(
    PagingData.from(
        listOf(
            article1,
            article2
        )
    )
)

val videosResponse = flowOf(
    PagingData.from(
        listOf(
            videoModel,
            videoModel2
        )
    )
)


val sportCategory = CategoryModel("sport", "Sport", true)

val selectedCategories = flowOf(listOf(sportCategory))

fun create1HourAgoTime() = DateTime().toDateTime(DateTimeZone.UTC).minusHours(1).toString()
