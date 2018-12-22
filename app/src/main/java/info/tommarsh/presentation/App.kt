package info.tommarsh.presentation

import android.app.Application
import android.content.Context
import info.tommarsh.presentation.di.ApplicationComponent
import info.tommarsh.presentation.di.ApplicationModule
import info.tommarsh.presentation.di.DaggerApplicationComponent
import info.tommarsh.presentation.di.NetworkModule

class App : Application() {

    private val graph: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .application(this)
            .appModule(ApplicationModule)
            .networkModule(NetworkModule)
            .build()
    }


    companion object {
        fun graph(context: Context) = (context.applicationContext as App).graph
    }
}