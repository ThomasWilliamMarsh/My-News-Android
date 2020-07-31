package info.tommarsh.mynews.core.category.data

import info.tommarsh.mynews.core.category.data.local.source.CategoryLocalDataStore
import info.tommarsh.mynews.core.util.ErrorLiveData
import javax.inject.Inject

class CategoryDataRepository
@Inject internal constructor(
    private val local: CategoryLocalDataStore,
    override val errors: ErrorLiveData
) : CategoryRepository {

    override suspend fun getSelectedCategories() = local.getSelectedCategories()

    override fun getCategories() = local.getCategories()

    override suspend fun updateCategory(id: String, selected: Boolean) =
        local.updateCategory(id, selected)
}