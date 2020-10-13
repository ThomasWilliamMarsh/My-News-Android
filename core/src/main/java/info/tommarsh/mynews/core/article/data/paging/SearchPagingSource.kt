package info.tommarsh.mynews.core.article.data.paging

import androidx.paging.PagingSource
import info.tommarsh.mynews.core.article.data.remote.source.ArticlesRemoteDataStore
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.model.Outcome

internal class SearchPagingSource constructor(
    private val query: String,
    private val remoteArticleSource: ArticlesRemoteDataStore
) : PagingSource<Int, ArticleModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        val page = params.key ?: 1

        return when (val outcome = remoteArticleSource.searchArticles(page, query)) {
            is Outcome.Success -> {
                val nextKey = if (outcome.data.isEmpty()) null else page + 1
                val previousKey = if(page == 1) null else page -1
                LoadResult.Page(
                    data = outcome.data,
                    nextKey = nextKey,
                    prevKey = previousKey
                )
            }
            is Outcome.Error -> LoadResult.Error(throwable = outcome.error)
        }
    }
}