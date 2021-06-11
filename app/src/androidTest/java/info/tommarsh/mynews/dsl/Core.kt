package info.tommarsh.mynews.dsl

import androidx.fragment.app.Fragment
import info.tommarsh.mynews.util.launchFragmentInHiltContainer


@DslMarker
annotation class UITestDSLMarker

fun given(givenBlock: Given.() -> Unit) {
    Given().apply(givenBlock)
}

fun whenever(wheneverBlock: Whenever.() -> Unit) {
    Whenever().apply(wheneverBlock)
}

fun then(thenBlock: Then.() -> Unit) {
    Then().apply(thenBlock)
}

@UITestDSLMarker
class Given

@UITestDSLMarker
class Whenever {

    inline fun <reified T : Fragment> launchesFragment() {
        launchFragmentInHiltContainer<T>()
    }
}

@UITestDSLMarker
class Then {
    fun sees(seesBlock: Sees.() -> Unit) {
        Sees().apply(seesBlock)
    }
}

class Sees