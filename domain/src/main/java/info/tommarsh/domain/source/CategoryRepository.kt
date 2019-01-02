package info.tommarsh.domain.source

import androidx.lifecycle.LiveData
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.domain.model.CategoryModel

interface CategoryRepository {

    val errors: ErrorLiveData

    fun getCategories(): LiveData<List<CategoryModel>>

    fun getSelectedCategories(): LiveData<List<CategoryModel>>
}