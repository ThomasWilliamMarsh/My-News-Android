package info.tommarsh.mynews.presentation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.work.Configuration
import androidx.work.WorkManager
import info.tommarsh.mynews.core.di.CoreComponentProvider
import info.tommarsh.mynews.core.di.DaggerCoreComponent
import info.tommarsh.mynews.core.offline.OfflineSyncScheduler

class NewsApp : Application(), CoreComponentProvider, Configuration.Provider {

    override val coreComponent by lazy {
        DaggerCoreComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        setNightMode()
        scheduleOfflineSync()
    }

    private fun setNightMode() {
        setDefaultNightMode(coreComponent.sharedPreferences().getNightMode())
    }

    private fun scheduleOfflineSync() {
        OfflineSyncScheduler(WorkManager.getInstance(this)).schedule()
    }

    override fun getWorkManagerConfiguration() = coreComponent.workManagerConfiguration()
}