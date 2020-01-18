package info.tommarsh.mynews.presentation

import android.app.Application
import android.content.Context
import info.tommarsh.mynews.core.di.CoreCreator
import info.tommarsh.mynews.presentation.di.DaggerHomeComponent
import info.tommarsh.mynews.presentation.di.HomeComponent

class NewsApp : Application() {

    private val graph: HomeComponent by lazy {
        DaggerHomeComponent
            .factory()
            .create(CoreCreator.create(this))
    }

    companion object {
        fun homeGraph(context: Context) = (context.applicationContext as NewsApp).graph
    }
}