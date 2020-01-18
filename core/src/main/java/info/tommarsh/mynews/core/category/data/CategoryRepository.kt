package info.tommarsh.mynews.core.category.data

import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.core.util.ErrorLiveData
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    val errors: ErrorLiveData

    fun getCategories(): Flow<List<CategoryModel>>

    fun getSelectedCategoriesStream(): Flow<List<CategoryModel>>

    suspend fun getSelectedCategories(): List<CategoryModel>

    suspend fun updateCategory(id: String, selected: Boolean)
}