package info.tommarsh.core.di

object CoreCreator {

    fun create(): CoreComponent {
        return DaggerCoreComponent
            .factory()
            .create()
    }
}