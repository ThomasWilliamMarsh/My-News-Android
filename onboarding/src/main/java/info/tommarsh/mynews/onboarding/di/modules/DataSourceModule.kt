package info.tommarsh.mynews.onboarding.di.modules

import dagger.Binds
import dagger.Module
import info.tommarsh.mynews.core.di.FeatureScope
import info.tommarsh.mynews.core.util.coroutines.CoroutineDispatcherProvider
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.onboarding.data.FirebaseOnBoardingDataSource
import info.tommarsh.mynews.onboarding.data.OnBoardingDataSource

@Module
internal abstract class DataSourceModule {

    @Binds
    @FeatureScope
    abstract fun bindOnBoardingDataSource(firebaseOnBoardingDataSource: FirebaseOnBoardingDataSource) : OnBoardingDataSource

    @Binds
    abstract fun bindDispatcherProvider(dispatcherProvider: CoroutineDispatcherProvider) : DispatcherProvider
}