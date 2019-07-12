package info.tommarsh.data.di

import android.content.Context

object DataCreator {

    fun create(context: Context): DataComponent {
        return DaggerDataComponent
            .factory()
            .create(context)
    }
}