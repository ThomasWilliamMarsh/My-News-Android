package info.tommarsh.mynews.presentation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.work.Configuration
import androidx.work.WorkManager
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import info.tommarsh.mynews.core.di.CoreComponentProvider
import info.tommarsh.mynews.core.di.DaggerCoreComponent
import info.tommarsh.mynews.core.offline.OfflineSyncScheduler
import info.tommarsh.presentation.R

class NewsApp : Application(), CoreComponentProvider, Configuration.Provider {

    override val coreComponent by lazy {
        DaggerCoreComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        setNightMode()
        scheduleOfflineSync()
        getRemoteConfigValues()
    }

    private fun setNightMode() {
        setDefaultNightMode(coreComponent.sharedPreferences().getNightMode())
    }

    private fun scheduleOfflineSync() {
        OfflineSyncScheduler(WorkManager.getInstance(this)).schedule()
    }

    private fun getRemoteConfigValues() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        remoteConfig.setDefaultsAsync(R.xml.remote_config_default_values)
        remoteConfig.fetchAndActivate()
    }

    override fun getWorkManagerConfiguration() = coreComponent.workManagerConfiguration()
}