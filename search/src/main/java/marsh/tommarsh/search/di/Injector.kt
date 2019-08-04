package marsh.tommarsh.search.di

import info.tommarsh.core.di.CoreCreator
import info.tommarsh.data.di.RepositoryCreator
import marsh.tommarsh.search.ui.SearchActivity

object Injector {

    fun SearchActivity.inject() {
        DaggerSearchComponent
            .factory()
            .create(CoreCreator.create(), RepositoryCreator.create(this))
            .inject(this)
    }
}