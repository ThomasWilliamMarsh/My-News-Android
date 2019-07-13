package marsh.tommarsh.di

import info.tommarsh.data.di.RepositoryCreator
import marsh.tommarsh.article.ArticleActivity

internal object ArticleInjector {

    fun ArticleActivity.inject() {
        DaggerArticleComponent
            .factory()
            .create(this, RepositoryCreator.create(this))
            .inject(this)
    }
}