package info.tommarsh.mynews.presentation.di

import androidx.fragment.app.Fragment
import info.tommarsh.mynews.core.di.provideCoreComponent
import info.tommarsh.mynews.presentation.NewsApp
import info.tommarsh.mynews.presentation.SplashActivity
import info.tommarsh.mynews.presentation.ui.categories.CategoriesFragment
import info.tommarsh.mynews.presentation.ui.top.TopNewsFragment
import info.tommarsh.mynews.presentation.ui.videos.VideosFragment

internal fun SplashActivity.inject() {
    provideCoreComponent()
        .inject(this)
}

internal fun TopNewsFragment.inject() {
    homeComponent()
        .inject(this)
}

internal fun CategoriesFragment.inject() {
    homeComponent()
        .inject(this)
}

internal fun VideosFragment.inject() {
    homeComponent()
        .inject(this)
}

internal fun Fragment.homeComponent() = (activity as HomeComponentProvider).homeComponent()


