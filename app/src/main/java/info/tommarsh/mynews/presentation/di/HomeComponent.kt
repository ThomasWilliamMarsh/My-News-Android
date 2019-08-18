package info.tommarsh.mynews.presentation.di

import dagger.Component
import info.tommarsh.mynews.core.di.CoreComponent
import info.tommarsh.mynews.core.di.FeatureScope
import info.tommarsh.mynews.presentation.ui.ArticlesActivity
import info.tommarsh.mynews.presentation.ui.categories.CategoriesFragment
import info.tommarsh.mynews.presentation.ui.top.TopNewsFragment
import info.tommarsh.mynews.presentation.ui.videos.VideosFragment
import info.tommarsh.mynews.repository.di.RepositoryComponent


@FeatureScope
@Component(
    dependencies = [CoreComponent::class, RepositoryComponent::class],
    modules = [(HomeModule::class), (ViewModelModule::class)]
)
interface HomeComponent {

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            repositoryComponent: RepositoryComponent
        ): HomeComponent
    }

    fun inject(fragment: TopNewsFragment)

    fun inject(fragment: CategoriesFragment)

    fun inject(fragment: VideosFragment)

    fun inject(activity: ArticlesActivity)
}