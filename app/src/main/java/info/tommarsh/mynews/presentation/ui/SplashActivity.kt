package info.tommarsh.mynews.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import info.tommarsh.mynews.core.di.provideCoreComponent
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.navigateToClass
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = provideCoreComponent().sharedPreferences()

        if(preferences.shouldShowOnBoarding()) {
            navigateToClass(ONBOARDING_ACTIVITY_CLASS)
        } else {
            startActivity(Intent(this, ArticlesActivity::class.java))
        }
    }

    companion object {
        private const val ONBOARDING_ACTIVITY_CLASS = "info.tommarsh.mynews.onboarding.ui.OnBoardingActivity"
    }
}