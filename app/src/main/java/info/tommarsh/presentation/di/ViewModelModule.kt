package info.tommarsh.presentation.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import info.tommarsh.core.di.ViewModelKey
import info.tommarsh.presentation.ui.article.categories.CategoriesViewModel
import info.tommarsh.presentation.ui.article.top.TopNewsViewModel
import info.tommarsh.presentation.ui.article.videos.VideosViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TopNewsViewModel::class)
    abstract fun bindTopNewsViewModel(viewModel: TopNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindCategoriesViewModel(viewModel: CategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideosViewModel::class)
    abstract fun bindVideosViewModel(viewModel: VideosViewModel): ViewModel
}