package info.tommarsh.presentation.di

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import info.tommarsh.presentation.utils.Navigator

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideActivity() = activity

    @Provides
    fun provideNavigator(activity: AppCompatActivity) = Navigator(activity)
}