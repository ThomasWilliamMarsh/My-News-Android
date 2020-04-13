package info.tommarsh.mynews.core.offline

import androidx.work.*
import java.util.concurrent.TimeUnit

//TODO: Hook up with shared preferences when we implement it.
class OfflineSyncScheduler
constructor(private val workManager: WorkManager) {

    companion object {
        private const val WORK_NAME = "offline_job"
    }

    fun schedule() {

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<OfflineWorker>(
            1,
            TimeUnit.HOURS
        ).setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}