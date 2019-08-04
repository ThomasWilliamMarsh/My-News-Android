package marsh.tommarsh.search.di

import dagger.Component
import info.tommarsh.core.di.CoreComponent
import info.tommarsh.core.di.FeatureScope
import info.tommarsh.data.di.RepositoryComponent
import marsh.tommarsh.search.ui.SearchActivity

@FeatureScope
@Component(
    dependencies = [CoreComponent::class, RepositoryComponent::class],
    modules = [SearchModule::class]
)
interface SearchComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent, repositoryComponent: RepositoryComponent) : SearchComponent
    }

    fun inject(searchActivity: SearchActivity)
}