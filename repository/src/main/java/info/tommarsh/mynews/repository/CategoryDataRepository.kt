package info.tommarsh.mynews.repository

import info.tommarsh.mynews.core.errors.ErrorLiveData
import info.tommarsh.mynews.core.repository.CategoryRepository
import info.tommarsh.mynews.repository.source.local.category.CategoryLocalDataStore
import javax.inject.Inject

class CategoryDataRepository
@Inject internal constructor(
    private val local: CategoryLocalDataStore,
    override val errors: ErrorLiveData
) : CategoryRepository {
    override fun getSelectedCategoriesStream() = local.getSelectedCategoriesStream()

    override suspend fun getSelectedCategories() = local.getSelectedCategories()

    override fun getCategories() = local.getCategories()

    override suspend fun updateCategory(id: String, selected: Boolean) =
        local.updateCategory(id, selected)
}