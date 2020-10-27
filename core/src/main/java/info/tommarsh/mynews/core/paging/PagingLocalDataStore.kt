package info.tommarsh.mynews.core.paging

import info.tommarsh.mynews.core.paging.source.Page
import info.tommarsh.mynews.core.paging.source.PagingDao
import javax.inject.Inject

class PagingLocalDataStore
@Inject internal constructor(private val dao: PagingDao) {

    suspend fun getPageForCategory(category: String): Int {
        return try {
            dao.getPageForCategory(category)
        } catch (throwable: Throwable) {
            FIRST_PAGE
        }
    }

    suspend fun setPageForCategory(category: String, page: Int) {
        dao.setPageForCategory(Page(category, page))
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}