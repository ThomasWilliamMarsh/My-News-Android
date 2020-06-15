package info.tommarsh.mynews.onboarding.data

import info.tommarsh.mynews.onboarding.model.Choices

internal interface OnBoardingDataSource {

    fun getOnBoardingChoices(key: String): Choices
}