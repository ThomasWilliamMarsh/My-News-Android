package info.tommarsh.mynews.onboarding.data

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nhaarman.mockitokotlin2.mock
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.onboarding.MockProvider.choices
import info.tommarsh.mynews.onboarding.model.Choices
import org.junit.Assert.assertEquals
import org.junit.Test

class FirebaseOnBoardingDataSourceTest {

    private val adapter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(Choices::class.java)

    private val remoteConfig = mock<FirebaseRemoteConfig> {
        on { getString("key") }.thenReturn(adapter.toJson(choices))
    }

    private val datasource = FirebaseOnBoardingDataSource(remoteConfig, adapter)


    @Test
    fun `Get onboarding choices from firebase`() {
        val expected = Outcome.Success(choices)

        val actual = datasource.getOnBoardingChoices("key")

        assertEquals(expected, actual)
    }
}