package info.tommarsh.mynews.core.model

interface ViewModel {
    fun contentsTheSame(other: ViewModel): Boolean
}