package info.tommarsh.mynews.article.di

import info.tommarsh.mynews.article.ui.ArticleActivity
import info.tommarsh.mynews.core.di.provideCoreComponent

fun ArticleActivity.inject() {
    DaggerArticleComponent.factory()
        .create(provideCoreComponent())
        .inject(this)
}