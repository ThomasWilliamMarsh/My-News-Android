package info.tommarsh.mynews.categories.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import info.tommarsh.mynews.categories.ui.CategoryChoiceViewModel
import info.tommarsh.mynews.core.di.ViewModelKey

@Module
abstract class CategoryModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoryChoiceViewModel::class)
    abstract fun bindCategoryChoiceViewModel(viewModel: CategoryChoiceViewModel): ViewModel
}