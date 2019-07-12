package info.tommarsh.presentation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import info.tommarsh.core.coroutines.CoroutineDispatcherProvider
import info.tommarsh.core.coroutines.DispatcherProvider

@Module(subcomponents = [ActivityComponent::class])
object ApplicationModule {

    @Provides
    @JvmStatic
    fun provideSharedPreferences(context: Context) =
        context.getSharedPreferences("default_preferences", Context.MODE_PRIVATE)

    @Provides
    @JvmStatic
    fun provideDispatcherProvider(coroutineDispatcherProvider: CoroutineDispatcherProvider): DispatcherProvider =
        coroutineDispatcherProvider
}
