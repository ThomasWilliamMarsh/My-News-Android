package info.tommarsh.mynews.tests

import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import info.tommarsh.mynews.UITest
import info.tommarsh.mynews.core.di.VideoRepositoryModule
import info.tommarsh.mynews.core.video.data.VideoRepository
import info.tommarsh.mynews.dsl.given
import info.tommarsh.mynews.dsl.then
import info.tommarsh.mynews.dsl.videos
import info.tommarsh.mynews.dsl.whenever
import info.tommarsh.mynews.home.ui.videos.VideosFragment
import info.tommarsh.mynews.util.videoModel
import info.tommarsh.mynews.util.videoModel2
import org.junit.Test
import org.mockito.Mock

@HiltAndroidTest
@UninstallModules(VideoRepositoryModule::class)
class VideosFragmentTest : UITest() {

    @BindValue
    @Mock
    lateinit var videoRepository: VideoRepository

    @Test
    fun successful_response_shows_list_of_videos() {

        given {
            videos {
                videoRepository returnsPlaylist listOf(videoModel, videoModel2)
            }
        }

        whenever {
            launchesFragment<VideosFragment>()
        }

        then {
            sees {
                videos {
                    mainVideo {
                        title = "Video title 1"
                        lastUpdated = "1 hour ago"
                    }

                    secondaryVideo {
                        title = "Video title 2"
                        lastUpdated = "1 hour ago"
                    }
                }
            }
        }
    }
}