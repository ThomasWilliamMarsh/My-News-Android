package info.tommarsh.mynews.search.di

import info.tommarsh.mynews.core.di.CoreCreator
import info.tommarsh.mynews.search.ui.SearchActivity

object Injector {

    fun SearchActivity.inject() {
        DaggerSearchComponent.factory()
            .create(CoreCreator.create(this))
            .inject(this)
    }
}