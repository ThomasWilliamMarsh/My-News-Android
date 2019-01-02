package info.tommarsh.domain.usecase

import info.tommarsh.domain.model.ArticleModel
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.domain.source.CategoryRepository
import javax.inject.Inject

class RefreshCategoryArticles
@Inject constructor(
    private val articleRepository: ArticleRepository,
    private val categoryRepository: CategoryRepository
) {

    suspend fun execute(): List<ArticleModel> {

        /**
        val result = mutableListOf<ArticleModel>()

        coroutineScope {
        val categories = categoryRepository.getCategories()
        categories.map {
        async { articleRepository.getArticlesForCategory(it.id) }
        }.forEach {
        result.addAll(it.await())
        }
        }

         **/

        return emptyList()
    }
}