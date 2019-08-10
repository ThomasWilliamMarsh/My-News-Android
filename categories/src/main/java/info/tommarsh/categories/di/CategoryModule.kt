package info.tommarsh.categories.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import info.tommarsh.core.di.ViewModelKey
import info.tommarsh.categories.ui.CategoryChoiceViewModel

@Module
abstract class CategoryModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoryChoiceViewModel::class)
    abstract fun bindCategoryChoiceViewModel(viewModel: CategoryChoiceViewModel): ViewModel
}