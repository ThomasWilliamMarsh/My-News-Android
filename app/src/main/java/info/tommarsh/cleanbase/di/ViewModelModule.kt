package info.tommarsh.cleanbase.di

import androidx.lifecycle.ViewModel
import com.thomasmarsh.presentation.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FeatureViewModel::class)
    abstract fun bindFruitViewModel(viewModel: FeatureViewModel): ViewModel
}