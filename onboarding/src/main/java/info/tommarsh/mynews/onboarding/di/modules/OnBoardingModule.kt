package info.tommarsh.mynews.onboarding.di.modules

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import info.tommarsh.mynews.core.di.FeatureScope
import info.tommarsh.mynews.onboarding.model.OnBoardingModel

@Module(includes = [DataSourceModule::class, ViewModelModule::class])
internal object OnBoardingModule {

    @Provides
    @FeatureScope
    fun provideOnboardingDeserialiser(): JsonAdapter<OnBoardingModel> {
        return Moshi.Builder()
            .build()
            .adapter(OnBoardingModel::class.java)
    }

    @Provides
    fun provideRemoteConfig() : FirebaseRemoteConfig {
        return FirebaseRemoteConfig.getInstance()
    }
}