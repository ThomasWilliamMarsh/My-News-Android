package info.tommarsh.mynews.presentation.model

import info.tommarsh.mynews.core.model.ViewModel

data class HeaderViewModel(val category: String) : ViewModel {

    override fun contentsTheSame(other: ViewModel): Boolean {
        return category == (other as? HeaderViewModel)?.category
    }
}