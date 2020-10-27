package info.tommarsh.mynews.onboarding.di.modules

import dagger.Binds
import dagger.Module
import info.tommarsh.mynews.onboarding.data.FirebaseOnBoardingDataSource
import info.tommarsh.mynews.onboarding.data.OnBoardingDataSource

@Module
internal abstract class DataSourceModule {

    @Binds
    abstract fun bindOnBoardingDataSource(firebaseOnBoardingDataSource: FirebaseOnBoardingDataSource): OnBoardingDataSource
}