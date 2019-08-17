package info.tommarsh.core.model

data class CategoryModel(
    val id: String,
    val name: String,
    val selected: Boolean
) : DomainModel