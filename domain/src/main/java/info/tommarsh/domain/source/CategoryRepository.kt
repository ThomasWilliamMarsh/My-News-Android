package info.tommarsh.domain.source

import androidx.lifecycle.LiveData
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.domain.model.CategoryModel

interface CategoryRepository {

    val errors: ErrorLiveData

    fun getCategories(): LiveData<List<CategoryModel>>

    fun getSelectedCategoriesStream(): LiveData<List<CategoryModel>>

    suspend fun getSelectedCategories(): List<CategoryModel>

    suspend fun updateCategory(id: String, selected: Boolean)
}