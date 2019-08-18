package info.tommarsh.mynews.categories.di

import dagger.Component
import info.tommarsh.mynews.categories.ui.CategoryChoiceActivity
import info.tommarsh.mynews.core.di.CoreComponent
import info.tommarsh.mynews.core.di.FeatureScope
import info.tommarsh.mynews.repository.di.RepositoryComponent

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