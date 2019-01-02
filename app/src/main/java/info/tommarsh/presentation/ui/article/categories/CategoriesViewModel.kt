package info.tommarsh.presentation.ui.article.categories

import info.tommarsh.domain.source.CategoryRepository
import info.tommarsh.domain.usecase.RefreshCategoryArticles
import info.tommarsh.presentation.ui.base.BaseViewModel
import javax.inject.Inject

class CategoriesViewModel
@Inject constructor(
    private val refreshCategoryArticles: RefreshCategoryArticles,
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {

    fun getSelectedCategories() = categoryRepository.getSelectedCategories()
}