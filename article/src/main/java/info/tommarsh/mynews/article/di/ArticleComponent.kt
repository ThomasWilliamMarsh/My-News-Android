package info.tommarsh.mynews.article.di

import dagger.Component
import info.tommarsh.mynews.article.ui.ArticleActivity
import info.tommarsh.mynews.core.di.CoreComponent
import info.tommarsh.mynews.core.di.FeatureScope

@Component(dependencies = [CoreComponent::class])
@FeatureScope
interface ArticleComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): ArticleComponent
    }

    fun inject(articleActivity: ArticleActivity)
}