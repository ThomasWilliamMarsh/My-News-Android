package info.tommarsh.mynews.onboarding.model

internal data class Choice(
    val id: String,
    val name: String
)

internal data class Choices(
    val choices: List<Choice>
)