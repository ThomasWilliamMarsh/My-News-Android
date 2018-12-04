package info.tommarsh.cleanbase.di

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import info.tommarsh.cleanbase.utils.Navigator

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideActivity() = activity

    @Provides
    fun provideNavigator(activity: AppCompatActivity) = Navigator(activity)
}