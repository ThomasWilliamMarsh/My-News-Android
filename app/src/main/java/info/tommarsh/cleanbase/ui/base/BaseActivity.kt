package info.tommarsh.cleanbase.ui.base

import androidx.appcompat.app.AppCompatActivity
import info.tommarsh.cleanbase.App.Companion.graph
import info.tommarsh.cleanbase.ViewModelFactory
import info.tommarsh.cleanbase.di.ActivityModule
import info.tommarsh.cleanbase.di.DaggerActivityComponent
import info.tommarsh.cleanbase.utils.Navigator
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {

    @Inject
    protected lateinit var navigator: Navigator

    @Inject
    protected lateinit var factory: ViewModelFactory

    protected val component by lazy {
        DaggerActivityComponent.builder()
            .applicationComponent(graph(this))
            .activityModule(ActivityModule(this))
            .build()
    }
}