package info.tommarsh.mynews.core.category.data

import info.tommarsh.mynews.core.category.domain.CategoryModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<List<CategoryModel>>

    fun getSelectedCategories(): Flow<List<CategoryModel>>

    suspend fun updateCategory(id: String, selected: Boolean)
}