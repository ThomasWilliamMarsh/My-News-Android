package info.tommarsh.mynews.categories.di

import info.tommarsh.mynews.categories.ui.CategoryChoiceActivity
import info.tommarsh.mynews.core.di.provideCoreComponent

internal fun CategoryChoiceActivity.inject() {
    DaggerCategoryComponent.factory()
        .create(provideCoreComponent())
        .inject(this)
}