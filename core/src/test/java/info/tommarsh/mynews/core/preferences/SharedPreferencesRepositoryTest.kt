package info.tommarsh.mynews.core.preferences

import android.app.UiModeManager.MODE_NIGHT_NO
import android.content.SharedPreferences
import info.tommarsh.mynews.core.preferences.SharedPreferencesRepository.Companion.KEY_PREFERENCE_COUNTRY
import info.tommarsh.mynews.core.preferences.SharedPreferencesRepository.Companion.KEY_PREFERENCE_NIGHT_MODE
import info.tommarsh.mynews.core.preferences.SharedPreferencesRepository.Companion.KEY_PREFERENCE_SHOW_ONBOARDING
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SharedPreferencesRepositoryTest : UnitTest<SharedPreferencesRepository>() {

    private val preferences = mock<SharedPreferences>()

    @Test
    fun `get country`() {
        val country = fixture<String>()
        whenever(preferences.getString(KEY_PREFERENCE_COUNTRY, "gb")).thenReturn(country)

        val actual = sut.getCountry()

        assertEquals(country, actual)
    }

    @Test
    fun `get night mode`() {
        val nightModePreference = fixture<Int>()
        whenever(
            preferences.getInt(
                KEY_PREFERENCE_NIGHT_MODE,
                MODE_NIGHT_NO
            )
        ).thenReturn(nightModePreference)

        val actual = sut.getNightMode()

        assertEquals(nightModePreference, actual)
    }

    @Test
    fun `get if onboarding should be shown`() {
        val showOnBoarding = fixture<Boolean>()
        whenever(preferences.getBoolean(KEY_PREFERENCE_SHOW_ONBOARDING, true))
            .thenReturn(showOnBoarding)

        val actual = sut.shouldShowOnBoarding()

        assertEquals(showOnBoarding, actual)
    }

    override fun createSut(): SharedPreferencesRepository {
        return SharedPreferencesRepository(preferences)
    }
}