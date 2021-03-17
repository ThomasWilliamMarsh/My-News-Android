package info.tommarsh.mynews.di

import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.paging.PagingData
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.video.data.VideoRepository
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import javax.inject.Inject

val videoModel =
    PlaylistItemModel(
        "videoId1",
        "Video title 1",
        DateTime().toDateTime(DateTimeZone.UTC).minusHours(1).toString(),
        "https://edit.co.uk/uploads/2016/12/Image-1-Alternatives-to-stock-photography-Thinkstock.jpg"
    )

val videoModel2 =
    PlaylistItemModel(
        "videoId2",
        "Video title 2",
        DateTime().toDateTime(DateTimeZone.UTC).minusHours(1).toString(),
        "https://cdn.theatlantic.com/static/mt/assets/science/cat_caviar.jpg"
    )

class FakeVideoRepository @Inject constructor() : VideoRepository {

    var playlistHasError = false

    override fun getPlaylist(pageSize: Int): Flow<PagingData<PlaylistItemModel>> = flow {
        if(playlistHasError) error("Error!")
        else emit(
            PagingData.from(
                listOf(
                    videoModel,
                    videoModel2
                )
            )
        )
    }
}

class FakePreferencesRepository @Inject constructor() : PreferencesRepository {
    override fun getNightMode(): Int {
        return MODE_NIGHT_YES
    }

    override fun toggleNightMode() {
    }

    override fun shouldShowOnBoarding(): Boolean {
        return false
    }

    override fun flagOnBoardingComplete() {
    }

    override fun saveCountry(country: String) {
    }

    override fun getCountry(): String {
        return "UK"
    }
}

class FakeArticleRepository @Inject constructor() : ArticleRepository {
    override fun getArticlesForCategory(
        category: String,
        pageSize: Int
    ): Flow<PagingData<ArticleModel>> {
        return flow { emit(PagingData.from(emptyList<ArticleModel>())) }
    }

    override fun searchArticles(query: String, pageSize: Int): Flow<PagingData<ArticleModel>> {
        return flow { emit(PagingData.from(emptyList<ArticleModel>())) }
    }
}

class FakeCategoryRepository @Inject constructor() : CategoryRepository {
    override fun getCategories(): Flow<List<CategoryModel>> {
        return flow { emit(emptyList<CategoryModel>()) }
    }

    override fun getSelectedCategories(): Flow<List<CategoryModel>> {
        return flow { emit(emptyList<CategoryModel>()) }
    }

    override suspend fun updateCategory(id: String, selected: Boolean) {
    }
}