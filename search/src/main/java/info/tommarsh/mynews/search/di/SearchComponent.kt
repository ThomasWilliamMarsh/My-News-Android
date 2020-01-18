package info.tommarsh.mynews.search.di

import dagger.Component
import info.tommarsh.mynews.core.di.CoreComponent
import info.tommarsh.mynews.core.di.FeatureScope
import info.tommarsh.mynews.search.ui.SearchActivity

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [SearchModule::class]
)
interface SearchComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): SearchComponent
    }

    fun inject(searchActivity: SearchActivity)
}