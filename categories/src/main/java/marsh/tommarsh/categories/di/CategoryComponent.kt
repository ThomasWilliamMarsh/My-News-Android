package marsh.tommarsh.categories.di

import dagger.Component
import info.tommarsh.core.di.CoreComponent
import info.tommarsh.core.di.FeatureScope
import info.tommarsh.data.di.RepositoryComponent
import marsh.tommarsh.categories.ui.CategoryChoiceActivity

@FeatureScope
@Component(
    dependencies = [CoreComponent::class, RepositoryComponent::class],
    modules = [CategoryModule::class]
)
interface CategoryComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent, repositoryComponent: RepositoryComponent): CategoryComponent
    }

    fun inject(categoryChoiceActivity: CategoryChoiceActivity)
}