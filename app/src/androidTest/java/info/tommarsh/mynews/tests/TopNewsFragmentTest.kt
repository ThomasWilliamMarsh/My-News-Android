package info.tommarsh.mynews.tests

import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import info.tommarsh.mynews.UITest
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.di.ArticleRepositoryModule
import info.tommarsh.mynews.dsl.articles
import info.tommarsh.mynews.dsl.given
import info.tommarsh.mynews.dsl.then
import info.tommarsh.mynews.dsl.whenever
import info.tommarsh.mynews.home.ui.top.TopNewsFragment
import info.tommarsh.mynews.util.article1
import info.tommarsh.mynews.util.article2
import org.junit.Test
import org.mockito.Mock

@HiltAndroidTest
@UninstallModules(ArticleRepositoryModule::class)
class TopNewsFragmentTest : UITest() {

    @BindValue
    @Mock
    lateinit var articleRepository: ArticleRepository

    @Test
    fun successful_response_shows_list_of_articles() {

        given {
            articles {
                articleRepository returnsTopStories listOf(article1, article2)
            }
        }

        whenever {
            launchesFragment<TopNewsFragment>()
        }

        then {
            sees {
                articles {
                    mainArticle {
                        title = "title1"
                        lastUpdated = "1 hour ago"
                    }
                    secondaryArticle {
                        title = "title2"
                        lastUpdated = "1 hour ago"
                    }
                }
            }
        }
    }
}