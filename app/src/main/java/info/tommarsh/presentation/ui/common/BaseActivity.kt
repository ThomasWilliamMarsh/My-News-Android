package info.tommarsh.presentation.ui.common

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import info.tommarsh.data.PreferencesRepository
import info.tommarsh.presentation.NewsApp.Companion.homeGraph
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    protected lateinit var sharedPreferencesRepository: PreferencesRepository

    protected val activityGraph by lazy {
        homeGraph(this)
    }

    protected fun observeNightMode() {
        sharedPreferencesRepository.observeNightModeChanges().observe(this, Observer { nightMode ->
            delegate.localNightMode = nightMode
            invalidateOptionsMenu()
        })
    }
}