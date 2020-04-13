package info.tommarsh.mynews.presentation.offline

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.util.coroutines.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext

class OfflineWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val coroutineDispatcher: CoroutineDispatcherProvider,
    private val articleRepository: ArticleRepository,
    private val categoryRepository: CategoryRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        withContext(coroutineDispatcher.work()) {
            articleRepository.refreshBreakingNews()
            articleRepository.refreshFeed(categoryRepository.getSelectedCategories())
        }

        //Always return success, it's not essential that do anything if it fails
        return Result.success()
    }
}