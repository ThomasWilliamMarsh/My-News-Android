package info.tommarsh.mynews.presentation.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.mynews.core.util.createDiffItemCallback
import info.tommarsh.mynews.core.util.loadUrl
import info.tommarsh.mynews.presentation.model.ArticleViewModel
import info.tommarsh.mynews.presentation.setClickListenerFor
import info.tommarsh.mynews.presentation.util.DelegateDiffCallback
import info.tommarsh.presentation.databinding.ItemCategoryArticleBinding

class CarouselAdapter : ListAdapter<ArticleViewModel, CarouselViewholder>(DIFFER) {

    companion object {
        val DIFFER = createDiffItemCallback<ArticleViewModel>{ old, new ->
            old.url == new.url
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewholder {
        val binding =
            ItemCategoryArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewholder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewholder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CarouselViewholder(private val binding: ItemCategoryArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: ArticleViewModel) {
        binding.categoryArticleName.text = article.title
        binding.categoryArticleUpdated.text = article.publishedAt
        if (article.urlToImage.isNotEmpty()) {
            binding.categoryArticleImage.loadUrl(article.urlToImage)
        }
        article.setClickListenerFor(itemView)
    }
}