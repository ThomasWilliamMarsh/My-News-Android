package info.tommarsh.mynews.onboarding.data

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.squareup.moshi.JsonAdapter
import info.tommarsh.mynews.onboarding.model.OnBoardingModel
import javax.inject.Inject

internal class FirebaseOnBoardingDataSource
@Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    private val deserialiser: JsonAdapter<OnBoardingModel>
) : OnBoardingDataSource {

    override fun getOnBoardingModel(key: String): OnBoardingModel {
        return deserialiser.fromJson(remoteConfig.getString(key))!!
    }
}