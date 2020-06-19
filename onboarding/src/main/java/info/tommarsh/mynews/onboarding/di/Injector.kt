package info.tommarsh.mynews.onboarding.di

import info.tommarsh.mynews.core.di.provideCoreComponent
import info.tommarsh.mynews.onboarding.ui.OnBoardingActivity

fun OnBoardingActivity.inject() {

    DaggerOnBoardingComponent.factory()
        .create(provideCoreComponent())
        .inject(this)
}