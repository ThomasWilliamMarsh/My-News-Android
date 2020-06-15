package info.tommarsh.mynews.onboarding.data

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.squareup.moshi.JsonAdapter
import info.tommarsh.mynews.onboarding.model.Choices
import javax.inject.Inject

internal class FirebaseOnBoardingDataSource
@Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    private val deserialiser: JsonAdapter<Choices>
) : OnBoardingDataSource {

    override fun getOnBoardingChoices(key: String): Choices {
        return deserialiser.fromJson(remoteConfig.getString(key))!!
    }
}