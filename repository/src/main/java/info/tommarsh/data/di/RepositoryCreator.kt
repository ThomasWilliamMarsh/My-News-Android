package info.tommarsh.data.di

import android.content.Context

object RepositoryCreator {

    fun create(context: Context): RepositoryComponent {
        return DaggerRepositoryComponent
            .factory()
            .create(context)
    }
}