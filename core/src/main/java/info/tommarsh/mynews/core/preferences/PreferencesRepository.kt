package info.tommarsh.mynews.core.preferences

interface PreferencesRepository {

    fun getNightMode() : Int

    fun toggleNightMode()

    fun shouldShowOnBoarding() : Boolean

    fun flagOnBoardingShown()
}