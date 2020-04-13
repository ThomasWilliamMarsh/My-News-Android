package info.tommarsh.mynews.presentation.di

import dagger.BindsInstance
import dagger.Component
import info.tommarsh.mynews.core.di.CoreComponent
import info.tommarsh.mynews.core.di.FeatureScope
import info.tommarsh.mynews.presentation.NewsApp
import info.tommarsh.mynews.presentation.ui.ArticlesActivity
import info.tommarsh.mynews.presentation.ui.categories.CategoriesFragment
import info.tommarsh.mynews.presentation.ui.top.TopNewsFragment
import info.tommarsh.mynews.presentation.ui.videos.VideosFragment

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [(HomeModule::class), (ViewModelModule::class), (OfflineModule::class)]
)
interface HomeComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance articlesActivity: ArticlesActivity,
            coreComponent: CoreComponent
        ): HomeComponent
    }

    fun inject(fragment: TopNewsFragment)

    fun inject(fragment: CategoriesFragment)

    fun inject(fragment: VideosFragment)

    fun inject(activity: ArticlesActivity)

    fun inject(app: NewsApp)
}