package info.tommarsh.presentation.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import info.tommarsh.core.di.ViewModelKey
import info.tommarsh.presentation.ui.article.categories.CategoriesViewModel
import info.tommarsh.presentation.ui.article.top.TopNewsViewModel
import info.tommarsh.presentation.ui.article.videos.VideosViewModel
import info.tommarsh.presentation.ui.categories.CategoryChoiceViewModel
import info.tommarsh.presentation.ui.search.SearchViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TopNewsViewModel::class)
    abstract fun bindFruitViewModel(viewModel: TopNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindCategoriesViewModel(viewModel: CategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideosViewModel::class)
    abstract fun bindVideosViewModel(viewModel: VideosViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryChoiceViewModel::class)
    abstract fun bindCategoryChoiceViewModel(viewModel: CategoryChoiceViewModel): ViewModel
}