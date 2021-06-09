package info.tommarsh.mynews.onboarding.model

data class Choice(
    val id: String,
    val name: String
)

data class Choices(
    val choices: List<Choice>
)