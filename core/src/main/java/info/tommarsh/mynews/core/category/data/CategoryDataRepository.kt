package info.tommarsh.mynews.core.category.data

import info.tommarsh.mynews.core.category.data.local.source.CategoryLocalDataStore
import javax.inject.Inject

class CategoryDataRepository
@Inject internal constructor(private val local: CategoryLocalDataStore) : CategoryRepository {

    override fun getSelectedCategories() = local.getSelectedCategories()

    override fun getCategories() = local.getCategories()

    override suspend fun updateCategory(id: String, selected: Boolean) =
        local.updateCategory(id, selected)
}