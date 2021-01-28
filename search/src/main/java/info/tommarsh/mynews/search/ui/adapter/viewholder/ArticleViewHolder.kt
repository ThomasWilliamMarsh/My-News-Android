package info.tommarsh.mynews.search.ui.adapter.viewholder

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.mynews.core.util.loadUrl
import info.tommarsh.mynews.search.model.SearchItemViewModel
import info.tommarsh.search.R
import info.tommarsh.search.databinding.ItemSearchArticleBinding

class ArticleViewHolder(private val binding: ItemSearchArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: SearchItemViewModel) = with(itemView) {
        binding.articleImage.loadUrl(article.urlToImage)
        binding.articleTitle.text = article.title
        binding.articleUpdated.text = article.publishedAt

        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_articleFragment,
            bundleOf("url" to article.urlToImage, "title" to article.title))
        }
    }
}