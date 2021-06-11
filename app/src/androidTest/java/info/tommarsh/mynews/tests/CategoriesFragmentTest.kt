package info.tommarsh.mynews.tests

import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import info.tommarsh.mynews.UITest
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.di.ArticleRepositoryModule
import info.tommarsh.mynews.core.di.CategoryRepositoryModule
import info.tommarsh.mynews.dsl.*
import info.tommarsh.mynews.home.ui.categories.CategoriesFragment
import info.tommarsh.mynews.util.article1
import info.tommarsh.mynews.util.article2
import info.tommarsh.mynews.util.sportCategory
import org.junit.Test
import org.mockito.Mock

@HiltAndroidTest
@UninstallModules(ArticleRepositoryModule::class, CategoryRepositoryModule::class)
class CategoriesFragmentTest : UITest() {

    @BindValue
    @Mock
    lateinit var articleRepository: ArticleRepository

    @BindValue
    @Mock
    lateinit var categoryRepository: CategoryRepository

    @Test
    fun no_categories_brings_up_add_categories_prompt() {

        given {
            categories {
                categoryRepository returnsSelectedCategories emptyList()
            }
        }

        whenever {
            launchesFragment<CategoriesFragment>()
        }

        then {
            sees {
                categories {
                    noCategoriesBlurb()
                }
            }
        }
    }

    @Test
    fun selected_categories_show_carousel_and_title() {
        given {
            categories {
                categoryRepository returnsSelectedCategories listOf(sportCategory)
            }

            articles {
                articleRepository returnsArticlesForSportCategory listOf(article1, article2)
            }
        }

        whenever {
            launchesFragment<CategoriesFragment>()
        }

        then {
            sees {
                categories {
                    carousel {
                        name = "Sport"
                    }
                }
            }
        }
    }
}