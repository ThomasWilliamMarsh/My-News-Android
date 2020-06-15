package info.tommarsh.mynews.core.preferences

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate.*
import javax.inject.Inject

class SharedPreferencesRepository
@Inject internal constructor(private val sharedPreferences: SharedPreferences) :
    PreferencesRepository {

    companion object {
        private const val KEY_PREFERENCE_NIGHT_MODE = "pref_night_mode"
        private const val KEY_PREFERENCE_SOURCES = "sources"
        private const val KEY_PREFERENCE_SHOW_ONBOARDING = "pref_on_boarding"
    }

    override fun getNightMode(): Int {
        return sharedPreferences.getInt(KEY_PREFERENCE_NIGHT_MODE, MODE_NIGHT_NO)
    }

    override fun toggleNightMode() {
        val nightMode = if (getNightMode() == MODE_NIGHT_YES) MODE_NIGHT_NO else MODE_NIGHT_YES
        sharedPreferences.edit().putInt(KEY_PREFERENCE_NIGHT_MODE, nightMode).apply()

        setDefaultNightMode(nightMode)
    }

    override fun shouldShowOnBoarding(): Boolean {
        return sharedPreferences.getBoolean(KEY_PREFERENCE_SHOW_ONBOARDING, true)
    }

    override fun flagOnBoardingComplete() {
        sharedPreferences.edit().putBoolean(KEY_PREFERENCE_SHOW_ONBOARDING, false).apply()
    }

    override fun saveSources(sources: List<String>) {
        sharedPreferences.edit().putStringSet(KEY_PREFERENCE_SOURCES, sources.toSet()).apply()
    }

    override fun getSources(): String {
        val sources =
            sharedPreferences.getStringSet(KEY_PREFERENCE_SOURCES, mutableSetOf("bbc-news"))!!
        return sources.joinToString()
    }
}