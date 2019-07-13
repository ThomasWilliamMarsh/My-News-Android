package marsh.tommarsh.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import info.tommarsh.core.di.FeatureScope
import info.tommarsh.data.di.RepositoryComponent
import marsh.tommarsh.article.ArticleActivity

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