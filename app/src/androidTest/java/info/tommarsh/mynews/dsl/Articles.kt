package info.tommarsh.mynews.dsl

import androidx.paging.PagingData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.home.R
import kotlinx.coroutines.flow.flowOf
import org.mockito.Mockito

fun Given.articles(articlesGivenBlock: ArticlesGiven.() -> Unit) {
    ArticlesGiven().apply(articlesGivenBlock)
}

fun Sees.articles(articlesSeesBlock: ArticlesSees.() -> Unit) {
    ArticlesSees().apply(articlesSeesBlock)
}

class ArticlesGiven {

    infix fun ArticleRepository.returnsArticlesForSportCategory(articles: List<ArticleModel>) {
        Mockito.`when`(getArticlesForCategory("sport", 5))
            .thenReturn(flowOf(PagingData.from(articles)))
    }

    infix fun ArticleRepository.returnsTopStories(articles: List<ArticleModel>) {
        Mockito.`when`(getArticlesForCategory("general"))
            .thenReturn(flowOf(PagingData.from(articles)))
    }
}

class ArticlesSees {
    fun mainArticle(mainArticleAssertionsBlock: MainArticleAssertions.() -> Unit) {
        MainArticleAssertions().apply(mainArticleAssertionsBlock)
    }

    fun secondaryArticle(secondaryArticleAssertionsBlock: SecondaryArticleAssertions.() -> Unit) {
        SecondaryArticleAssertions().apply(secondaryArticleAssertionsBlock)
    }
}

class MainArticleAssertions {

    var title: String = ""
        set(value) {
            onView(withId(R.id.main_article_title))
                .check(ViewAssertions.matches(withText(value)))
            field = value
        }

    var lastUpdated: String = ""
        set(value) {
            onView(withId(R.id.main_article_updated))
                .check(ViewAssertions.matches(withText(value)))
            field = value
        }
}

class SecondaryArticleAssertions {

    var title: String = ""
        set(value) {
            onView(withId(R.id.article_title))
                .check(ViewAssertions.matches(withText(value)))
            field = value
        }

    var lastUpdated: String = ""
        set(value) {
            onView(withId(R.id.article_updated))
                .check(ViewAssertions.matches(withText(value)))
            field = value
        }
}