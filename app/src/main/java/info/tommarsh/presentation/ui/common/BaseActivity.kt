package info.tommarsh.presentation.ui.common

import androidx.appcompat.app.AppCompatActivity
import info.tommarsh.presentation.NewsApp.Companion.applicationGraph

abstract class BaseActivity : AppCompatActivity() {

    protected val activityGraph by lazy {
        applicationGraph(this)
            .activityComponent
            .create(this)
    }
}