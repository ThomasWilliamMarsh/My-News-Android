package info.tommarsh.presentation.ui.base

import androidx.appcompat.app.AppCompatActivity
import info.tommarsh.presentation.App.Companion.graph
import info.tommarsh.presentation.ViewModelFactory
import info.tommarsh.presentation.di.ActivityModule
import info.tommarsh.presentation.di.DaggerActivityComponent
import info.tommarsh.presentation.utils.Navigator
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