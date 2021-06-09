package info.tommarsh.mynews.core.util.coroutines

import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagingCacheImpl @Inject constructor() : PagingCache {

    override fun <T : Any> cache(
        flow: Flow<PagingData<T>>,
        scope: CoroutineScope
    ): Flow<PagingData<T>> {
        return flow.cachedIn(scope)
    }
}