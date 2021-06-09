package info.tommarsh.mynews.onboarding.data

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import info.tommarsh.mynews.core.model.Resource
import info.tommarsh.mynews.onboarding.model.Choices
import info.tommarsh.mynews.test.UnitTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock

class FirebaseOnBoardingDataSourceTest : UnitTest<FirebaseOnBoardingDataSource>() {

    private val key = fixture<String>()
    private val choices = fixture<Choices>()

    private val adapter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(Choices::class.java)

    private val remoteConfig = mock<FirebaseRemoteConfig> {
        on { getString(key) }.thenReturn(adapter.toJson(choices))
    }

    @Test
    fun `Get onboarding choices from firebase`() {
        val expected = Resource.Data(choices)

        val actual = sut.getOnBoardingChoices(key)

        assertEquals(expected, actual)
    }

    override fun createSut(): FirebaseOnBoardingDataSource {
        return FirebaseOnBoardingDataSource(remoteConfig, adapter)
    }
}