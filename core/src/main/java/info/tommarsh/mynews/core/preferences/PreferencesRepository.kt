package info.tommarsh.mynews.core.preferences

import androidx.lifecycle.LiveData

interface PreferencesRepository {

    fun observeNightModeChanges() : LiveData<Int>

    fun getNightMode() : Int

    fun toggleNightMode()
}