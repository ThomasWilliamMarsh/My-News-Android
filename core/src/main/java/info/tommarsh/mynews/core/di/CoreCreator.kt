package info.tommarsh.mynews.core.di

object CoreCreator {

    fun create(): CoreComponent {
        return DaggerCoreComponent.factory()
            .create()
    }
}