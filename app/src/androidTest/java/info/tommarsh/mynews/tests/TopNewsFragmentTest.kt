package info.tommarsh.mynews.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import info.tommarsh.mynews.articlesResponse
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.di.ArticleRepositoryModule
import info.tommarsh.mynews.home.R
import info.tommarsh.mynews.home.ui.top.TopNewsFragment
import info.tommarsh.mynews.launchFragmentInHiltContainer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@HiltAndroidTest
@UninstallModules(ArticleRepositoryModule::class)
class TopNewsFragmentTest {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @BindValue
    @Mock
    lateinit var articleRepository: ArticleRepository

    @Before
    fun inject_mocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun successful_response_shows_list_of_articles() {
        `when`(articleRepository.getArticlesForCategory("general"))
            .thenReturn(articlesResponse)

        launchFragmentInHiltContainer<TopNewsFragment>()

        //Ensure that first article is a large card
        onView(withId(R.id.main_article_title))
            .check(matches(withText("title1")))

        onView(withId(R.id.main_article_updated))
            .check(matches(withText("1 hour ago")))

        //Ensure that second article is a small card
        onView(withId(R.id.article_title))
            .check(matches(withText("title2")))

        onView(withId(R.id.article_updated))
            .check(matches(withText("1 hour ago")))


    }
}