package info.tommarsh.mynews.onboarding.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.onboarding.databinding.ActivityOnboardingBinding

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    private val binding by lazy { ActivityOnboardingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}