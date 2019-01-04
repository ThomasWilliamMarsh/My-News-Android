package info.tommarsh.presentation.model

import info.tommarsh.core.ViewModel

data class ArticleViewModel(
    val author: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val category: String
) : ViewModel