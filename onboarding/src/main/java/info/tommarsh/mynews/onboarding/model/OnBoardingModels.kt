package info.tommarsh.mynews.onboarding.model

internal data class OnBoardingModel(
    val animationFile: String,
    val choices: List<ChoiceModel>
)

internal data class ChoiceModel(
    val id: String,
    val name: String
)