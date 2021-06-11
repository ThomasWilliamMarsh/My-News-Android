package info.tommarsh.mynews.dsl

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.home.R
import kotlinx.coroutines.flow.flowOf
import org.mockito.Mockito

fun Given.categories(categoriesGiven: CategoriesGiven.() -> Unit) {
    CategoriesGiven().apply(categoriesGiven)
}

fun Sees.categories(catgoriesSees: CategoriesSees.() -> Unit) {
    CategoriesSees().apply(catgoriesSees)
}

class CategoriesGiven {

    infix fun CategoryRepository.returnsSelectedCategories(selectedCategories: List<CategoryModel>) {
        Mockito.`when`(getSelectedCategories()).thenReturn(flowOf(selectedCategories))
    }
}

class CategoriesSees {

    fun noCategoriesBlurb() {
        onView(withId(R.id.add_categories_blurb))
            .check(matches(isDisplayed()))
    }

    fun carousel(categoriesCarouselAssertionsBlock: CategoriesCarouselAssertions.() -> Unit) {
        CategoriesCarouselAssertions().apply(categoriesCarouselAssertionsBlock)
    }
}

class CategoriesCarouselAssertions {

    var name: String = ""
        set(value) {
            field = value
            onView(withId(R.id.carousel_name))
                .check(matches(withText("Sport")))
        }
}