package info.tommarsh.mynews.presentation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.HiltAndroidApp
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.presentation.R
import javax.inject.Inject

@HiltAndroidApp
class NewsApp : Application() {

    @Inject
    lateinit var preferences: PreferencesRepository

    override fun onCreate() {
        super.onCreate()
        setNightMode()
        getRemoteConfigValues()
    }

    private fun setNightMode() {
        setDefaultNightMode(preferences.getNightMode())
    }

    private fun getRemoteConfigValues() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        remoteConfig.setDefaultsAsync(R.xml.remote_config_default_values)
        remoteConfig.fetchAndActivate()
    }
}