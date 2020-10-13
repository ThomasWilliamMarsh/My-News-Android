package info.tommarsh.mynews.core.article.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import info.tommarsh.mynews.core.article.data.local.model.toDomainModel
import info.tommarsh.mynews.core.article.data.local.source.ArticlesLocalDataStore
import info.tommarsh.mynews.core.article.data.remote.source.ArticlesRemoteDataStore
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.util.ErrorLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleDataRepository
@Inject internal constructor(
    private val local: ArticlesLocalDataStore,
    private val remote: ArticlesRemoteDataStore,
    private val pagingConfig: PagingConfig,
    override val errors: ErrorLiveData
) : ArticleRepository {

    override fun getBreakingNews(): Flow<PagingData<ArticleModel>> {
        return Pager(
            config = pagingConfig,
            remoteMediator = BreakingNewsRemoteMediator(remote, local),
            pagingSourceFactory = { local.getBreakingNews() }
        ).flow.map { page -> page.map { article -> article.toDomainModel() } }
    }

    override fun getFeed(): Flow<List<ArticleModel>> = local.getFeed()

    override suspend fun searchArticles(query: String) = remote.searchArticles(query)

}