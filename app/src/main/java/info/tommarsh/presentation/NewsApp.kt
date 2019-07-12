package info.tommarsh.presentation

import android.app.Application
import android.content.Context
import info.tommarsh.core.di.CoreCreator
import info.tommarsh.data.di.DataCreator
import info.tommarsh.presentation.di.ApplicationComponent
import info.tommarsh.presentation.di.DaggerApplicationComponent

class NewsApp : Application() {

    private val graph: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .factory()
            .create(CoreCreator.create(), DataCreator.create(this))
    }

    companion object {
        fun applicationGraph(context: Context) = (context.applicationContext as NewsApp).graph
    }
}