package marsh.tommarsh.di

import info.tommarsh.data.di.DataCreator
import marsh.tommarsh.article.ArticleActivity

internal object ArticleInjector {

    fun ArticleActivity.inject() {
        DaggerArticleComponent
            .factory()
            .create(this, DataCreator.create(this))
            .inject(this)
    }
}