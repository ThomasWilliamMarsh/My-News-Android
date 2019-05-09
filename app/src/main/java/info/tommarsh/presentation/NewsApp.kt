package info.tommarsh.presentation

import android.app.Application
import android.content.Context
import info.tommarsh.presentation.di.ApplicationComponent
import info.tommarsh.presentation.di.DaggerApplicationComponent

class NewsApp : Application() {

    private val graph: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .factory()
            .create(this)
    }


    companion object {
        fun graph(context: Context) = (context.applicationContext as NewsApp).graph
    }
}