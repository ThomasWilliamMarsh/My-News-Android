package info.tommarsh.mynews.onboarding.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import info.tommarsh.mynews.onboarding.databinding.ActivityOnboardingBinding
import info.tommarsh.mynews.onboarding.di.inject
import info.tommarsh.mynews.onboarding.ui.util.OnBoardingFragmentFactory
import javax.inject.Inject

class OnBoardingActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: OnBoardingFragmentFactory

    private val binding by lazy { ActivityOnboardingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}