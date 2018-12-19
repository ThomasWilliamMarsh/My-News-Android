package info.tommarsh.presentation.di

import dagger.Component
import info.tommarsh.presentation.ui.article.ArticlesActivity

@Component(dependencies = [(ApplicationComponent::class)], modules = [(ActivityModule::class)])
interface ActivityComponent {
    fun inject(activity: ArticlesActivity)
}