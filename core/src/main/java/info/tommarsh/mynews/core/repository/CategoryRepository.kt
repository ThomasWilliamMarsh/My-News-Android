package info.tommarsh.mynews.core.repository

import info.tommarsh.mynews.core.errors.ErrorLiveData
import info.tommarsh.mynews.core.model.CategoryModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    val errors: ErrorLiveData

    fun getCategories(): Flow<List<CategoryModel>>

    fun getSelectedCategoriesStream(): Flow<List<CategoryModel>>

    suspend fun getSelectedCategories(): List<CategoryModel>

    suspend fun updateCategory(id: String, selected: Boolean)
}