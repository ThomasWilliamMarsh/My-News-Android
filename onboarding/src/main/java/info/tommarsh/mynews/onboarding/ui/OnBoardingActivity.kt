package info.tommarsh.mynews.onboarding.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitcompat.SplitCompat
import dagger.hilt.android.EntryPointAccessors
import info.tommarsh.mynews.onboarding.databinding.ActivityOnboardingBinding
import info.tommarsh.mynews.onboarding.di.DaggerOnBoardingComponent
import info.tommarsh.mynews.onboarding.di.OnBoardingComponent
import info.tommarsh.mynews.presentation.di.OnBoardingDependencies

class OnBoardingActivity : AppCompatActivity() {

    private val binding by lazy { ActivityOnboardingBinding.inflate(layoutInflater) }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.installActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    internal val component: OnBoardingComponent by lazy {
        DaggerOnBoardingComponent.factory()
            .create(
                this,
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    OnBoardingDependencies::class.java
                )
            )
    }
}