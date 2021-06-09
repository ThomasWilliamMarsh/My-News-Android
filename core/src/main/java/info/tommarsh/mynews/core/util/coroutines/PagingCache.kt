package info.tommarsh.mynews.core.util.coroutines

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface PagingCache {

    fun <T : Any> cache(
        flow: Flow<PagingData<T>>,
        scope: CoroutineScope
    ): Flow<PagingData<T>>
}