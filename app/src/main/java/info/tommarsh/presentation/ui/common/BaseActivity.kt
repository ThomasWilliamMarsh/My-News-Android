package info.tommarsh.presentation.ui.common

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import info.tommarsh.data.PreferencesRepository
import info.tommarsh.presentation.NewsApp.Companion.applicationGraph
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    protected lateinit var sharedPreferencesRepository: PreferencesRepository

    protected val activityGraph by lazy {
        applicationGraph(this)
            .activityComponent
            .create(this)
    }

    protected fun observeNightMode() {
        sharedPreferencesRepository.observeNightModeChanges().observe(this) { nightMode ->
            delegate.localNightMode = nightMode
            invalidateOptionsMenu()
        }
    }
}