package info.tommarsh.mynews.onboarding.model

internal data class OnBoardingModel(
    val animation: String,
    val choices: List<ChoiceModel>,
    val deeplink: String?
)

internal data class ChoiceModel(
    val id: String,
    val name: String
)