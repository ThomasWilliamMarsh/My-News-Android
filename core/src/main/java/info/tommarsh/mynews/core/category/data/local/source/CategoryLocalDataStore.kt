package info.tommarsh.mynews.core.category.data.local.source

import info.tommarsh.mynews.core.category.data.local.model.toDomainModel
import info.tommarsh.mynews.core.category.domain.CategoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryLocalDataStore
@Inject internal constructor(private val dao: CategoryDao) {

    fun getCategories(): Flow<List<CategoryModel>> =
        dao.getCategories()
            .map { databaseModels -> databaseModels.map { model -> model.toDomainModel() } }

    fun getSelectedCategories(): Flow<List<CategoryModel>> =
        dao.getSelectedCategories()
            .map { databaseModels -> databaseModels.map { model -> model.toDomainModel() } }

    suspend fun updateCategory(id: String, selected: Boolean) {
        dao.updateCategory(id, selected)
    }
}