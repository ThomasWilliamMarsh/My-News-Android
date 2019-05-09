package info.tommarsh.presentation.ui.common

import androidx.appcompat.app.AppCompatActivity
import info.tommarsh.presentation.NewsApp

abstract class BaseActivity : AppCompatActivity() {

    protected val activityGraph by lazy {
        NewsApp.graph(this)
            .activityComponent
            .create(this)
    }
}