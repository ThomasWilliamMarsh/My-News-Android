package marsh.tommarsh.categories.di

import info.tommarsh.core.di.CoreCreator
import info.tommarsh.data.di.RepositoryCreator
import marsh.tommarsh.categories.ui.CategoryChoiceActivity

object Injector {

    fun CategoryChoiceActivity.inject() {
        DaggerCategoryComponent
            .factory()
            .create(CoreCreator.create(), RepositoryCreator.create(this))
            .inject(this)
    }
}