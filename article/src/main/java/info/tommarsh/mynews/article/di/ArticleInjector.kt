package info.tommarsh.mynews.article.di

import info.tommarsh.mynews.article.ui.ArticleActivity
import info.tommarsh.mynews.repository.di.RepositoryCreator

internal object ArticleInjector {

    fun ArticleActivity.inject() {
        DaggerArticleComponent.factory()
            .create(this, RepositoryCreator.create(this))
            .inject(this)
    }
}