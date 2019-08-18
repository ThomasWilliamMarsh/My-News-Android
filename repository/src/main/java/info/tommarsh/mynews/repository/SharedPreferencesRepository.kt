package info.tommarsh.mynews.repository

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import info.tommarsh.mynews.core.repository.PreferencesRepository
import javax.inject.Inject

class SharedPreferencesRepository
@Inject internal constructor(private val sharedPreferences: SharedPreferences) :
    PreferencesRepository {

    companion object {
        private const val KEY_PREFERENCE_NIGHT_MODE = "pref_night_mode"
    }

    private val _nightMode = MutableLiveData<Int>()

    private val preferenceChangedListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        when (key) {
            KEY_PREFERENCE_NIGHT_MODE -> _nightMode.value =
                sharedPreferences.getInt(KEY_PREFERENCE_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangedListener)
    }

    override fun getNightMode(): Int {
        return sharedPreferences.getInt(KEY_PREFERENCE_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun observeNightModeChanges(): LiveData<Int> {
        _nightMode.value = getNightMode()
        return _nightMode
    }

    override fun toggleNightMode() {
        sharedPreferences.edit {
            putInt(
                KEY_PREFERENCE_NIGHT_MODE, when (getNightMode()) {
                    AppCompatDelegate.MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }
    }
}