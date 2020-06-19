package info.tommarsh.mynews.onboarding.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import info.tommarsh.mynews.onboarding.data.FirebaseOnBoardingDataSource
import info.tommarsh.mynews.onboarding.data.OnBoardingDataSource
import info.tommarsh.mynews.onboarding.di.OnBoardingComponent

@Module
internal abstract class DataSourceModule {

    @Binds
    abstract fun bindOnBoardingDataSource(firebaseOnBoardingDataSource: FirebaseOnBoardingDataSource): OnBoardingDataSource
}