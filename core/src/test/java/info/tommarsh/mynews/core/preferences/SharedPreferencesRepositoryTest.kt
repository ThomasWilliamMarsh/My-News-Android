package info.tommarsh.mynews.core.preferences

import android.app.UiModeManager.MODE_NIGHT_NO
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.preferences.SharedPreferencesRepository.Companion.KEY_PREFERENCE_COUNTRY
import info.tommarsh.mynews.core.preferences.SharedPreferencesRepository.Companion.KEY_PREFERENCE_NIGHT_MODE
import info.tommarsh.mynews.core.preferences.SharedPreferencesRepository.Companion.KEY_PREFERENCE_SHOW_ONBOARDING
import junit.framework.Assert.assertEquals
import org.junit.Test

class SharedPreferencesRepositoryTest {

    private val preferences = mock<SharedPreferences>()

    private val repository = SharedPreferencesRepository(preferences)

    @Test
    fun `get country`() {
        val expected = "uk"
        whenever(preferences.getString(KEY_PREFERENCE_COUNTRY, "gb")).thenReturn("uk")

        val actual = repository.getCountry()

        assertEquals(expected, actual)
    }

    @Test
    fun `get night mode`() {
        val expected = MODE_NIGHT_NO
        whenever(
            preferences.getInt(
                KEY_PREFERENCE_NIGHT_MODE,
                AppCompatDelegate.MODE_NIGHT_NO
            )
        ).thenReturn(MODE_NIGHT_NO)

        val actual = repository.getNightMode()

        assertEquals(expected, actual)
    }

    @Test
    fun `get if onboarding should be shown`() {
        val expected = true
        whenever(preferences.getBoolean(KEY_PREFERENCE_SHOW_ONBOARDING, true)).thenReturn(true)

        val actual = repository.shouldShowOnBoarding()

        assertEquals(expected, actual)
    }
}