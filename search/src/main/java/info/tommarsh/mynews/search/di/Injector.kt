package info.tommarsh.mynews.search.di

import info.tommarsh.mynews.core.di.provideCoreComponent
import info.tommarsh.mynews.search.ui.SearchActivity

internal fun SearchActivity.inject() {
    DaggerSearchComponent.factory()
        .create(provideCoreComponent())
        .inject(this)
}