package info.tommarsh.mynews.home.ui

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import info.tommarsh.mynews.core.navigator.ClickEvent
import info.tommarsh.mynews.home.R

internal fun Fragment.consumeClick(event: ClickEvent) {
    when (event) {
        is ClickEvent.Search -> findNavController().navigate(
            R.id.action_homeFragment_to_search_graph
        )
        is ClickEvent.Categories -> findNavController().navigate(
            R.id.action_homeFragment_to_category_choice_graph
        )
        is ClickEvent.Article -> findNavController().navigate(
            R.id.action_homeFragment_to_article_nav_graph,
            bundleOf(
                "url" to event.imageUrl,
                "title" to event.title,
                "webUrl" to event.webUrl,
                "content" to event.content
            )
        )
        is ClickEvent.OnBoarding -> findNavController().navigate(
            R.id.action_homeFragment_to_onboardingGraph,
        )
    }
}