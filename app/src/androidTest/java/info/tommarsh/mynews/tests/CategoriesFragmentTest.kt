package info.tommarsh.mynews.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import info.tommarsh.mynews.articlesResponse
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.di.ArticleRepositoryModule
import info.tommarsh.mynews.core.di.CategoryRepositoryModule
import info.tommarsh.mynews.home.R
import info.tommarsh.mynews.home.ui.categories.CategoriesFragment
import info.tommarsh.mynews.launchFragmentInHiltContainer
import info.tommarsh.mynews.selectedCategories
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@UninstallModules(ArticleRepositoryModule::class, CategoryRepositoryModule::class)
@HiltAndroidTest
class CategoriesFragmentTest {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @BindValue
    @Mock
    lateinit var articleRepository: ArticleRepository

    @BindValue
    @Mock
    lateinit var categoryRepository: CategoryRepository

    @Before
    fun inject_mocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun no_categories_brings_up_add_categories_prompt() {
        `when`(categoryRepository.getSelectedCategories())
            .thenReturn(flowOf(emptyList()))

        launchFragmentInHiltContainer<CategoriesFragment>()

        onView(withId(R.id.add_categories_blurb))
            .check(matches(isDisplayed()))
    }

    @Test
    fun selected_categories_show_carousel_and_title() {
        `when`(categoryRepository.getSelectedCategories())
            .thenReturn(selectedCategories)

        `when`(articleRepository.getArticlesForCategory("sport", 5))
            .thenReturn(articlesResponse)

        launchFragmentInHiltContainer<CategoriesFragment>()

        onView(withId(R.id.carousel_name))
            .check(matches(withText("Sport")))

        onView(withId(R.id.carousel_items))
            .check(matches(isDisplayed()))

        onView(withText("title1"))
            .check(matches(isDisplayed()))
    }
}