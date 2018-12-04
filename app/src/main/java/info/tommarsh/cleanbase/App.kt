package info.tommarsh.cleanbase

import android.app.Application
import android.content.Context
import info.tommarsh.cleanbase.di.ApplicationComponent
import info.tommarsh.cleanbase.di.ApplicationModule
import info.tommarsh.cleanbase.di.DaggerApplicationComponent
import info.tommarsh.cleanbase.di.NetworkModule

class App : Application() {

    private val graph: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .networkModule(NetworkModule())
            .build()
    }

    companion object {
        fun graph(context: Context) = (context.applicationContext as App).graph
    }
}