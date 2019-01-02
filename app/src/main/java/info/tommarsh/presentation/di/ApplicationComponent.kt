package info.tommarsh.presentation.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import info.tommarsh.presentation.ui.article.categories.CategoriesFragment
import info.tommarsh.presentation.ui.article.top.TopNewsFragment
import info.tommarsh.presentation.ui.categories.CategoryChoiceActivity
import info.tommarsh.presentation.ui.search.SearchActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class), (NetworkModule::class), (ViewModelModule::class), (RepositoryModule::class)])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent

        fun appModule(module: ApplicationModule): Builder

        fun networkModule(module: NetworkModule): Builder
    }

    fun inject(fragment: TopNewsFragment)

    fun inject(fragment: CategoriesFragment)

    fun inject(activity: SearchActivity)

    fun inject(activity: CategoryChoiceActivity)
}