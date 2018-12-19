package info.tommarsh.data.source.remote

import info.tommarsh.core.network.NetworkHelper
import info.tommarsh.core.network.Outcome
import info.tommarsh.data.model.remote.mapper.ArticleResponseMapper
import info.tommarsh.domain.model.ArticleModel
import javax.inject.Inject

class RemoteDataStore
@Inject constructor(
    private val mapper: ArticleResponseMapper,
    private val networkHelper: NetworkHelper,
    private val api: ArticleApiService
) {

    fun getBreakingNews(): Outcome<List<ArticleModel>> {
        return networkHelper.callApi(api.getBreakingNews(), mapper)
    }
}