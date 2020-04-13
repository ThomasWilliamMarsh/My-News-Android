package info.tommarsh.mynews.core.di

import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import dagger.Module
import dagger.Provides
import info.tommarsh.mynews.core.offline.OfflineWorkerFactory

@Module
object OfflineModule {

    @Provides
    fun provideWorkManagerConfiguration(offlineWorkerFactory: OfflineWorkerFactory): Configuration {
        val workerFactory = DelegatingWorkerFactory().also { it.addFactory(offlineWorkerFactory) }
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}