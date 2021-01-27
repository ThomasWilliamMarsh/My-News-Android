package info.tommarsh.mynews.onboarding.data

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.squareup.moshi.JsonAdapter
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Resource
import info.tommarsh.mynews.onboarding.model.Choices
import javax.inject.Inject

internal class FirebaseOnBoardingDataSource
@Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    private val adapter: JsonAdapter<Choices>
) : OnBoardingDataSource {

    override fun getOnBoardingChoices(key: String): Resource<Choices> {
        return try {
            val data = adapter.fromJson(remoteConfig.getString(key))!!
            Resource.Data(data)
        } catch (throwable: Throwable) {
            Resource.Error(NetworkException.ServerException())
        }
    }
}