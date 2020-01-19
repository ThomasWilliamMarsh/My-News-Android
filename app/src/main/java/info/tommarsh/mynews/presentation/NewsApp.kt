package info.tommarsh.mynews.presentation

import android.app.Application
import info.tommarsh.mynews.core.di.CoreComponent
import info.tommarsh.mynews.core.di.CoreComponentProvider
import info.tommarsh.mynews.core.di.DaggerCoreComponent

class NewsApp : Application(), CoreComponentProvider {

    override fun coreComponent(): CoreComponent {
        return DaggerCoreComponent.factory()
            .create(this)
    }
}