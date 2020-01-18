package info.tommarsh.mynews.categories.di

import dagger.Component
import info.tommarsh.mynews.categories.ui.CategoryChoiceActivity
import info.tommarsh.mynews.core.di.CoreComponent
import info.tommarsh.mynews.core.di.FeatureScope

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [CategoryModule::class]
)
interface CategoryComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): CategoryComponent
    }

    fun inject(categoryChoiceActivity: CategoryChoiceActivity)
}