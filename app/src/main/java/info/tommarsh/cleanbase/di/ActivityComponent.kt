package info.tommarsh.cleanbase.di

import dagger.Component
import info.tommarsh.cleanbase.ui.feature.FeatureActivity

@Component(dependencies = [(ApplicationComponent::class)], modules = [(ActivityModule::class)])
interface ActivityComponent {
    fun inject(activity: FeatureActivity)
}