package info.tommarsh.mynews.categories.di

import info.tommarsh.mynews.categories.ui.CategoryChoiceActivity
import info.tommarsh.mynews.core.di.CoreCreator

object Injector {

    fun CategoryChoiceActivity.inject() {
        DaggerCategoryComponent.factory()
            .create(CoreCreator.create(this))
            .inject(this)
    }
}