package info.tommarsh.mynews.core.preferences

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate.*
import javax.inject.Inject

class SharedPreferencesRepository
@Inject internal constructor(private val sharedPreferences: SharedPreferences) :
    PreferencesRepository {

    companion object {
        internal const val KEY_PREFERENCE_NIGHT_MODE = "pref_night_mode"
        internal const val KEY_PREFERENCE_COUNTRY = "country"
        internal const val KEY_PREFERENCE_SHOW_ONBOARDING = "pref_on_boarding"
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

    override fun saveCountry(country: String) {
        sharedPreferences.edit().putString(KEY_PREFERENCE_COUNTRY, country).apply()
    }

    override fun getCountry(): String {
        return sharedPreferences.getString(KEY_PREFERENCE_COUNTRY, "gb")!!
    }
}