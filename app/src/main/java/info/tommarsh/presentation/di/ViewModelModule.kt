package info.tommarsh.presentation.di

import androidx.lifecycle.ViewModel
import com.thomasmarsh.presentation.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import info.tommarsh.presentation.ui.article.top.TopNewsViewModel
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
}