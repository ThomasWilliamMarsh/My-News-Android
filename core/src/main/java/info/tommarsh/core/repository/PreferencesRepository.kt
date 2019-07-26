package info.tommarsh.core.repository

import androidx.lifecycle.LiveData

interface PreferencesRepository {

    fun observeNightModeChanges() : LiveData<Int>

    fun getNightMode() : Int

    fun toggleNightMode()
}