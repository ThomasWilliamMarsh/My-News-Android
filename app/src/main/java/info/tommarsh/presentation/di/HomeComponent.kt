package info.tommarsh.presentation.di

import dagger.Component
import info.tommarsh.core.di.CoreComponent
import info.tommarsh.core.di.FeatureScope
import info.tommarsh.data.di.RepositoryComponent
import info.tommarsh.presentation.ui.article.ArticlesActivity
import info.tommarsh.presentation.ui.article.categories.CategoriesFragment
import info.tommarsh.presentation.ui.article.top.TopNewsFragment
import info.tommarsh.presentation.ui.article.videos.VideosFragment
import info.tommarsh.presentation.ui.search.SearchActivity

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

    fun inject(activity: SearchActivity)

    fun inject(activity: ArticlesActivity)
}