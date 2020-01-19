package info.tommarsh.mynews.core.preferences

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.*
import javax.inject.Inject

class SharedPreferencesRepository
@Inject internal constructor(private val sharedPreferences: SharedPreferences) :
    PreferencesRepository {

    companion object {
        private const val KEY_PREFERENCE_NIGHT_MODE = "pref_night_mode"
    }

    override fun getNightMode(): Int {
        return sharedPreferences.getInt(KEY_PREFERENCE_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun toggleNightMode() {
        val nightMode = if (getNightMode() == MODE_NIGHT_YES) MODE_NIGHT_NO else MODE_NIGHT_YES
        sharedPreferences.edit().putInt(KEY_PREFERENCE_NIGHT_MODE, nightMode).apply()

        setDefaultNightMode(nightMode)
    }
}