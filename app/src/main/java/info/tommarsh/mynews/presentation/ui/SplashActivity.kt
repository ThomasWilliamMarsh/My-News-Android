package info.tommarsh.mynews.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.preferences.SharedPreferencesRepository
import info.tommarsh.mynews.core.util.navigateToClass
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var preferences: SharedPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (preferences.shouldShowOnBoarding()) {
            navigateToClass(ONBOARDING_ACTIVITY_CLASS)
        } else {
            startActivity(Intent(this, ArticlesActivity::class.java))
        }

        finish()
    }

    companion object {
        private const val ONBOARDING_ACTIVITY_CLASS =
            "info.tommarsh.mynews.onboarding.ui.OnBoardingActivity"
    }
}