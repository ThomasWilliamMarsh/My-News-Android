package info.tommarsh.mynews.article.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import info.tommarsh.mynews.article.ui.ArticleActivity
import info.tommarsh.mynews.core.di.FeatureScope
import info.tommarsh.mynews.repository.di.RepositoryComponent

@Component(dependencies = [RepositoryComponent::class])
@FeatureScope
interface ArticleComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            repositoryComponent: RepositoryComponent
        ): ArticleComponent
    }

    fun inject(articleActivity: ArticleActivity)
}