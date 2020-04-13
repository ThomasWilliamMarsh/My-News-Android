package info.tommarsh.mynews.core.offline

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.util.coroutines.CoroutineDispatcherProvider
import javax.inject.Inject

class OfflineWorkerFactory
@Inject constructor(
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val articleRepository: ArticleRepository,
    private val categoryRepository: CategoryRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return OfflineWorker(
            appContext,
            workerParameters,
            coroutineDispatcherProvider,
            articleRepository,
            categoryRepository
        )
    }
}