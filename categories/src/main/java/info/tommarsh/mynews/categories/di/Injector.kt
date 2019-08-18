package info.tommarsh.mynews.categories.di

import info.tommarsh.mynews.categories.ui.CategoryChoiceActivity
import info.tommarsh.mynews.core.di.CoreCreator
import info.tommarsh.mynews.repository.di.RepositoryCreator

object Injector {

    fun CategoryChoiceActivity.inject() {
        DaggerCategoryComponent.factory()
            .create(CoreCreator.create(), RepositoryCreator.create(this))
            .inject(this)
    }
}