package info.tommarsh.mynews.core.repository

import androidx.lifecycle.LiveData
import info.tommarsh.mynews.core.errors.ErrorLiveData
import info.tommarsh.mynews.core.model.CategoryModel

interface CategoryRepository {

    val errors: ErrorLiveData

    fun getCategories(): LiveData<List<CategoryModel>>

    fun getSelectedCategoriesStream(): LiveData<List<CategoryModel>>

    suspend fun getSelectedCategories(): List<CategoryModel>

    suspend fun updateCategory(id: String, selected: Boolean)
}