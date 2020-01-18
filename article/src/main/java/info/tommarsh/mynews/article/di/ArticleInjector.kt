package info.tommarsh.mynews.article.di

import info.tommarsh.mynews.article.ui.ArticleActivity
import info.tommarsh.mynews.core.di.CoreCreator

internal object ArticleInjector {

    fun ArticleActivity.inject() {
        DaggerArticleComponent.factory()
            .create(CoreCreator.create(this))
            .inject(this)
    }
}