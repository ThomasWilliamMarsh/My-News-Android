package info.tommarsh.mynews.home

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate.*
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.HiltAndroidApp
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import javax.inject.Inject

@HiltAndroidApp
class NewsApp : Application() {

    @Inject
    lateinit var preferences: PreferencesRepository

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        setNightMode()
        getRemoteConfigValues()
    }

    private fun setNightMode() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
        } else {
            setDefaultNightMode(MODE_NIGHT_AUTO_BATTERY)
        }
    }

    private fun getRemoteConfigValues() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        remoteConfig.setDefaultsAsync(R.xml.remote_config_default_values)
        remoteConfig.fetchAndActivate()
    }
}