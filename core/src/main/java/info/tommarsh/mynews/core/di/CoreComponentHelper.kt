package info.tommarsh.mynews.core.di

import android.app.Activity
import android.content.Context

object CoreComponentHelper {

    fun coreComponent(context: Context): CoreComponent {
        return (context.applicationContext as? CoreComponentProvider)?.coreComponent()
            ?: throw IllegalStateException("Application context must have a CoreComponentProvider")
    }
}

fun Activity.provideCoreComponent() = CoreComponentHelper.coreComponent(this)