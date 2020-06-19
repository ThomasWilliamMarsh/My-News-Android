package info.tommarsh.mynews.onboarding

import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.onboarding.model.Choice
import info.tommarsh.mynews.onboarding.model.Choices

internal object MockProvider {
    val choices = Choices(
        listOf(
            Choice("id1", "choice1"),
            Choice("id2", "choice2")
        )
    )

    val exception = NetworkException.ServerException()

}