package info.tommarsh.mynews.onboarding.di.modules

import android.content.Context
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import info.tommarsh.mynews.onboarding.model.Choices

@Module
internal object OnBoardingModule {

    @Provides
    fun provideOnboardingDeserialiser(): JsonAdapter<Choices> {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(Choices::class.java)
    }

    @Provides
    fun provideRemoteConfig(): FirebaseRemoteConfig {
        return FirebaseRemoteConfig.getInstance()
    }

    @Provides
    fun provideSplitInstallManager(context: Context): SplitInstallManager {
        return SplitInstallManagerFactory.create(context)
    }
}