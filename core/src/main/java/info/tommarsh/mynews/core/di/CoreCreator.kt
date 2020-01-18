package info.tommarsh.mynews.core.di

import android.content.Context

object CoreCreator {

    fun create(context: Context): CoreComponent {
        return DaggerCoreComponent.factory()
            .create(context)
    }
}