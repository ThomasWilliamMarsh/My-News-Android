package info.tommarsh.mynews.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.navigateToClass
import info.tommarsh.mynews.presentation.ui.ArticlesActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {


    @Inject
    lateinit var sharedPreferencesRepository: PreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        if(true) {
            navigateToClass(ONBOARDING_ACTIVITY_CLASS)
        } else {
            startActivity(Intent(this, ArticlesActivity::class.java))
        }
    }

    companion object {
        private const val ONBOARDING_ACTIVITY_CLASS = "info.tommarsh.mynews.onboarding.ui.OnBoardingActivity"
    }
}