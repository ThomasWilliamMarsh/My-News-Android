package info.tommarsh.presentation.model

import info.tommarsh.domain.model.CategoryModel

object MockModelProvider {

    //region category
    val categoryModel = CategoryModel("id", "name", false)
    val categoryViewModel = CategoryViewModel("id", "name", false)
    //endregion
}