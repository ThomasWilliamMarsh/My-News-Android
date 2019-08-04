package marsh.tommarsh.search.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import info.tommarsh.core.di.ViewModelKey
import marsh.tommarsh.search.ui.SearchViewModel

@Module
abstract class SearchModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel) : ViewModel
}