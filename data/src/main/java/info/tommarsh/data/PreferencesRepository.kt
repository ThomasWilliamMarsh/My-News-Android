package info.tommarsh.data

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class PreferencesRepository
@Inject internal constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val KEY_PREFERENCE_NIGHT_MODE = "pref_night_mode"
    }

    private val _nightMode = MutableLiveData<Int>()

    val nightMode: Int
        get() = sharedPreferences.getInt(KEY_PREFERENCE_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO)

    private val preferenceChangedListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        when (key) {
            KEY_PREFERENCE_NIGHT_MODE -> _nightMode.value =
                sharedPreferences.getInt(KEY_PREFERENCE_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangedListener)
    }

    fun observeNightModeChanges(): LiveData<Int> {
        _nightMode.value = nightMode
        return _nightMode
    }

    fun toggleNightMode() {
        sharedPreferences.edit {
            putInt(
                KEY_PREFERENCE_NIGHT_MODE, when (nightMode) {
                    AppCompatDelegate.MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }
    }
}