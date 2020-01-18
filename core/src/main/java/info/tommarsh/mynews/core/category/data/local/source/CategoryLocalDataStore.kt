package info.tommarsh.mynews.core.category.data.local.source

import info.tommarsh.mynews.core.category.data.local.model.mapper.CategoryDataToDomainMapper
import info.tommarsh.mynews.core.category.domain.CategoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CategoryLocalDataStore
@Inject constructor(
    private val dao: CategoryDao,
    private val dataMapper: CategoryDataToDomainMapper
) {

    fun getCategories(): Flow<List<CategoryModel>> =
        dao.getCategories().map { dataMapper.map(it) }

    fun getSelectedCategoriesStream(): Flow<List<CategoryModel>> =
        dao.getSelectedCategoriesStream().map { dataMapper.map(it) }

    suspend fun getSelectedCategories(): List<CategoryModel> =
        dataMapper.map(dao.getSelectedCategories())

    suspend fun updateCategory(id: String, selected: Boolean) {
        dao.updateCategory(id, selected)
    }
}