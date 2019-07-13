package info.tommarsh.data

import androidx.lifecycle.LiveData
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.data.source.local.category.CategoryLocalDataStore
import info.tommarsh.domain.model.CategoryModel
import info.tommarsh.domain.source.CategoryRepository
import javax.inject.Inject

class CategoryDataRepository
@Inject internal constructor(
    private val local: CategoryLocalDataStore,
    override val errors: ErrorLiveData
) : CategoryRepository {
    override fun getSelectedCategoriesStream() = local.getSelectedCategoriesStream()

    override suspend fun getSelectedCategories() = local.getSelectedCategories()

    override fun getCategories(): LiveData<List<CategoryModel>> = local.getCategories()

    override suspend fun updateCategory(id: String, selected: Boolean) = local.updateCategory(id, selected)
}