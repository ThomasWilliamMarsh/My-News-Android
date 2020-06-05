package info.tommarsh.mynews.onboarding.di

import info.tommarsh.mynews.onboarding.ui.OnBoardingActivity

fun OnBoardingActivity.inject() {

    DaggerOnBoardingComponent.factory()
        .create()
        .inject(this)
}