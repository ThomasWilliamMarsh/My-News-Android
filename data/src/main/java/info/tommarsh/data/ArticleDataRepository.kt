package info.tommarsh.data

import androidx.lifecycle.LiveData
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.core.network.Outcome
import info.tommarsh.data.source.local.LocalDataStore
import info.tommarsh.data.source.remote.RemoteDataStore
import info.tommarsh.domain.model.ArticleModel
import info.tommarsh.domain.source.ArticleRepository
import javax.inject.Inject

class ArticleDataRepository
@Inject constructor(
    private val local: LocalDataStore,
    private val remote: RemoteDataStore
) : ArticleRepository {

    override val errors = ErrorLiveData()

    override fun getBreakingNewsObservable(source: String): LiveData<List<ArticleModel>> = local.getBreakingNews()

    override fun refreshBreakingNews() {
        val networkItems = remote.getBreakingNews()
        when (networkItems) {
            is Outcome.Success -> local.saveBreakingNews(networkItems.data)
            is Outcome.Error -> errors.setError(networkItems.error)
        }
    }

    override fun searchArticles(query: String) = remote.searchArticles(query)

    override fun getCategory(category: String): LiveData<List<ArticleModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}