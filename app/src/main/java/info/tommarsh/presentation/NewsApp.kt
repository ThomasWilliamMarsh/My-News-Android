package info.tommarsh.presentation

import android.app.Application
import android.content.Context
import info.tommarsh.core.di.CoreCreator
import info.tommarsh.data.di.RepositoryCreator
import info.tommarsh.presentation.di.DaggerHomeComponent
import info.tommarsh.presentation.di.HomeComponent

class NewsApp : Application() {

    private val graph: HomeComponent by lazy {
        DaggerHomeComponent
            .factory()
            .create(CoreCreator.create(), RepositoryCreator.create(this))
    }

    companion object {
        fun homeGraph(context: Context) = (context.applicationContext as NewsApp).graph
    }
}