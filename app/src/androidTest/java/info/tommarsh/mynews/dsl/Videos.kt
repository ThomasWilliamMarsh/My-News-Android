package info.tommarsh.mynews.dsl

import androidx.paging.PagingData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import info.tommarsh.mynews.core.video.data.VideoRepository
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel
import info.tommarsh.mynews.home.R
import kotlinx.coroutines.flow.flowOf
import org.mockito.Mockito

fun Given.videos(videosGivenBlock: VideoGiven.() -> Unit) {
    VideoGiven().apply(videosGivenBlock)
}

fun Sees.videos(videosSeesBlock: VideoSees.() -> Unit) {
    VideoSees().apply(videosSeesBlock)
}

class VideoGiven {

    infix fun VideoRepository.returnsPlaylist(playlist: List<PlaylistItemModel>) {
        Mockito.`when`(getPlaylist())
            .thenReturn(flowOf(PagingData.from(playlist)))
    }
}

class VideoSees {
    fun mainVideo(mainVideoAssertionsBlock: MainVideoAssertions.() -> Unit) {
        MainVideoAssertions().apply(mainVideoAssertionsBlock)
    }

    fun secondaryVideo(secondaryVideoAssertionsBlock: SecondaryVideoAssertions.() -> Unit) {
        SecondaryVideoAssertions().apply(secondaryVideoAssertionsBlock)
    }
}

class MainVideoAssertions {

    var title: String = ""
        set(value) {
            onView(withId(R.id.main_video_title))
                .check(ViewAssertions.matches(withText(value)))
            field = value
        }

    var lastUpdated: String = ""
        set(value) {
            onView(withId(R.id.main_video_updated))
                .check(ViewAssertions.matches(withText(value)))
            field = value
        }
}

class SecondaryVideoAssertions {

    var title: String = ""
        set(value) {
            onView(withId(R.id.video_title))
                .check(ViewAssertions.matches(withText(value)))
            field = value
        }

    var lastUpdated: String = ""
        set(value) {
            onView(withId(R.id.video_updated))
                .check(ViewAssertions.matches(withText(value)))
            field = value
        }
}