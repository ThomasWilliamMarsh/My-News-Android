package info.tommarsh.mynews.categories

import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.core.category.domain.CategoryModel

object MockModelProvider {

    //region category
    val categoryModel = CategoryModel("id", "name", false)
    val categoryViewModel = CategoryViewModel("id", "name", false)
    //endregion
}