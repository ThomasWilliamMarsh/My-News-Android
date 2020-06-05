package info.tommarsh.mynews.onboarding.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import info.tommarsh.mynews.onboarding.di.inject
import info.tommarsh.mynews.onboarding.ui.util.OnBoardingFragmentFactory
import javax.inject.Inject

class OnBoardingActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: OnBoardingFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        inject()
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState, persistentState)
    }
}