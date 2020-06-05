package info.tommarsh.mynews.onboarding.data

import info.tommarsh.mynews.onboarding.model.OnBoardingModel

internal interface OnBoardingDataSource {

    fun getOnBoardingModel(key: String): OnBoardingModel
}