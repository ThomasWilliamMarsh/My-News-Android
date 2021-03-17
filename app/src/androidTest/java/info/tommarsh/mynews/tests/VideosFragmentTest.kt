package info.tommarsh.mynews.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import info.tommarsh.mynews.di.FakeVideoRepository
import info.tommarsh.mynews.home.R
import info.tommarsh.mynews.home.ui.videos.VideosFragment
import info.tommarsh.mynews.launchFragmentInHiltContainer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class VideosFragmentTest {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @Inject
    lateinit var videoRepository: FakeVideoRepository

    @Before
    fun init() {
        rule.inject()
    }

    @Test
    fun successful_response_shows_list_of_videos() {
        launchFragmentInHiltContainer<VideosFragment>()

        //Ensure that first video is a large card
        onView(withId(R.id.main_video_title))
            .check(matches(withText("Video title 1")))

        onView(withId(R.id.main_video_updated))
            .check(matches(withText("1 hour ago")))

        //Ensure that second video is a small card

        onView(withId(R.id.video_title))
            .check(matches(withText("Video title 2")))

        onView(withId(R.id.video_updated))
            .check(matches(withText("1 hour ago")))
    }
}