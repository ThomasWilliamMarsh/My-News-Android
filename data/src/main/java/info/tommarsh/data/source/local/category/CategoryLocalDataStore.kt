package info.tommarsh.data.source.local.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import info.tommarsh.data.model.local.mapper.CategoryDataToDomainMapper
import info.tommarsh.data.model.local.mapper.CategoryDomainToDataMapper
import info.tommarsh.domain.model.CategoryModel
import javax.inject.Inject

class CategoryLocalDataStore
@Inject constructor(
    private val dao: CategoryDao,
    private val dataMapper: CategoryDataToDomainMapper,
    private val domainMapper: CategoryDomainToDataMapper
) {

    fun getCategories(): LiveData<List<CategoryModel>> =
        Transformations.map(dao.getCategories()) { model ->
            model.map { dataMapper.map(it) }
        }

    fun getSelectedCategories(): LiveData<List<CategoryModel>> =
        Transformations.map(dao.getSelectedCategories()) { model ->
            model.map { dataMapper.map(it) }
        }

    suspend fun updateCategory(category: CategoryModel) {
        val model = domainMapper.map(category)
        dao.updateCategory(model)
    }
}