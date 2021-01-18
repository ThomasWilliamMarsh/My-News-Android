package info.tommarsh.mynews.presentation.ui.top

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.mynews.core.util.createDiffItemCallback
import info.tommarsh.mynews.core.util.loadUrl
import info.tommarsh.mynews.presentation.model.ArticleViewModel
import info.tommarsh.mynews.presentation.setClickListenerFor
import info.tommarsh.presentation.databinding.ItemArticleBinding
import info.tommarsh.presentation.databinding.ItemMainArticleBinding

class TopNewsAdapter :
    PagingDataAdapter<ArticleViewModel, RecyclerView.ViewHolder>(DIFFER) {

    companion object {
        const val TYPE_PRIMARY_ARTICLE = 1
        const val TYPE_SECONDARY_ARTICLE = 2
        val DIFFER = createDiffItemCallback<ArticleViewModel> { old, new ->
            old.url == new.url
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = getItem(position)!!
        when (holder) {
            is PrimaryViewHolder -> holder.bind(article)
            is SecondaryViewHolder -> holder.bind(article)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_PRIMARY_ARTICLE -> PrimaryViewHolder(
                binding = ItemMainArticleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TYPE_SECONDARY_ARTICLE -> SecondaryViewHolder(
                binding = ItemArticleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid view type for Top News!")
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_PRIMARY_ARTICLE
            else -> TYPE_SECONDARY_ARTICLE
        }
    }
}

private class PrimaryViewHolder(private val binding: ItemMainArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: ArticleViewModel) {
        binding.mainArticleImage.loadUrl(article.urlToImage)
        binding.mainArticleTitle.text = article.title
        binding.mainArticleUpdated.text = article.publishedAt

        article.setClickListenerFor(itemView)
    }
}

private class SecondaryViewHolder(private val binding: ItemArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: ArticleViewModel) {
        binding.articleImage.loadUrl(article.urlToImage)
        binding.articleTitle.text = article.title
        binding.articleUpdated.text = article.publishedAt

        article.setClickListenerFor(itemView)
    }
}