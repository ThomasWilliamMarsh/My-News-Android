package info.tommarsh.mynews.presentation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.HiltAndroidApp
import info.tommarsh.mynews.core.offline.OfflineSyncScheduler
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.presentation.R
import javax.inject.Inject

@HiltAndroidApp
class NewsApp : Application(), Configuration.Provider {

    @Inject
    lateinit var preferences: PreferencesRepository

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        setNightMode()
        scheduleOfflineSync()
        getRemoteConfigValues()
    }

    private fun setNightMode() {
        setDefaultNightMode(preferences.getNightMode())
    }

    private fun scheduleOfflineSync() {
        OfflineSyncScheduler(WorkManager.getInstance(this)).schedule()
    }

    private fun getRemoteConfigValues() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        remoteConfig.setDefaultsAsync(R.xml.remote_config_default_values)
        remoteConfig.fetchAndActivate()
    }

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()
}