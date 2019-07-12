package marsh.tommarsh.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import info.tommarsh.core.di.FeatureScope
import info.tommarsh.data.di.DataComponent
import marsh.tommarsh.article.ArticleActivity

@Component(dependencies = [DataComponent::class])
@FeatureScope
interface ArticleComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            dataComponent: DataComponent
        ): ArticleComponent
    }

    fun inject(articleActivity: ArticleActivity)
}