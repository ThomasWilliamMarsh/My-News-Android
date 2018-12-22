package info.tommarsh.presentation.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import info.tommarsh.presentation.ui.article.top.TopNewsFragment
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
}