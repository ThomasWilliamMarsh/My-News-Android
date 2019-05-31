package info.tommarsh.presentation.di

import androidx.appcompat.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Subcomponent
import info.tommarsh.presentation.ui.article.ArticlesActivity
import info.tommarsh.presentation.ui.article.categories.CategoriesFragment
import info.tommarsh.presentation.ui.article.top.TopNewsFragment
import info.tommarsh.presentation.ui.article.videos.VideosFragment
import info.tommarsh.presentation.ui.categories.CategoryChoiceActivity
import info.tommarsh.presentation.ui.search.SearchActivity

@Subcomponent
interface ActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: AppCompatActivity): ActivityComponent
    }

    fun inject(fragment: TopNewsFragment)

    fun inject(fragment: CategoriesFragment)

    fun inject(fragment: VideosFragment)

    fun inject(activity: SearchActivity)

    fun inject(activity: CategoryChoiceActivity)

    fun inject(activity: ArticlesActivity)
}