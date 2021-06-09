package info.tommarsh.mynews.core.article.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import info.tommarsh.mynews.core.article.data.remote.source.ArticlesRemoteDataStore
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.model.Resource

class SearchPagingSource internal constructor(
    private val query: String,
    private val remoteArticleSource: ArticlesRemoteDataStore
) : PagingSource<Int, ArticleModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        val page = params.key ?: 1

        return when (val resource = remoteArticleSource.searchArticles(page, query)) {
            is Resource.Data -> {
                val nextKey = if (resource.data.isEmpty()) null else page + 1
                val previousKey = if (page == 1) null else page - 1
                LoadResult.Page(
                    data = resource.data,
                    nextKey = nextKey,
                    prevKey = previousKey
                )
            }
            is Resource.Error -> LoadResult.Error(throwable = resource.error)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleModel>) = 1
}