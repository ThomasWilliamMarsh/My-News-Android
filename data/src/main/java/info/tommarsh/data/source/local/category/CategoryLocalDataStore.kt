package info.tommarsh.data.source.local.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import info.tommarsh.data.model.local.mapper.CategoryDataToDomainMapper
import info.tommarsh.domain.model.CategoryModel
import javax.inject.Inject

internal class CategoryLocalDataStore
@Inject constructor(
    private val dao: CategoryDao,
    private val dataMapper: CategoryDataToDomainMapper
) {

    fun getCategories(): LiveData<List<CategoryModel>> =
        dao.getCategories().map(dataMapper::map)

    fun getSelectedCategoriesStream(): LiveData<List<CategoryModel>> =
        dao.getSelectedCategoriesStream().map(dataMapper::map)

    suspend fun getSelectedCategories(): List<CategoryModel> =
        dataMapper.map(dao.getSelectedCategories())

    suspend fun updateCategory(id: String, selected: Boolean) {
        dao.updateCategory(id, selected)
    }
}