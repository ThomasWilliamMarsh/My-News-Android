package info.tommarsh.mynews.paging

import androidx.paging.PagingData
import info.tommarsh.mynews.core.util.coroutines.PagingCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class NoPagingCache : PagingCache {

    override fun <T : Any> cache(
        flow: Flow<PagingData<T>>,
        scope: CoroutineScope
    ): Flow<PagingData<T>> {
        return flow
    }
}