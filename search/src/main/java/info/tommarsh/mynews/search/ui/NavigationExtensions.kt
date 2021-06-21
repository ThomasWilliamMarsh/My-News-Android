package info.tommarsh.mynews.search.ui

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import info.tommarsh.mynews.core.navigator.ClickEvent
import info.tommarsh.mynews.search.R

internal fun Fragment.consumeClick(event: ClickEvent) {
    when (event) {
        is ClickEvent.Article -> findNavController().navigate(
            R.id.action_searchFragment_to_articleFragment,
            bundleOf(
                "url" to event.imageUrl,
                "title" to event.title,
                "webUrl" to event.webUrl,
                "content" to event.content
            )
        )
        else -> throw IllegalArgumentException("Unhandled click event $event")
    }
}