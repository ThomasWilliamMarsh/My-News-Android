package info.tommarsh.mynews.presentation.ui

import android.content.Intent
import android.net.Uri
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import info.tommarsh.mynews.core.model.ViewModel
import info.tommarsh.mynews.core.util.loadUrl
import info.tommarsh.mynews.presentation.model.ArticleViewModel
import info.tommarsh.mynews.presentation.model.CarouselViewModel
import info.tommarsh.mynews.presentation.model.HeaderViewModel
import info.tommarsh.mynews.presentation.model.PlaylistItemViewModel
import info.tommarsh.mynews.presentation.setClickListenerFor
import info.tommarsh.mynews.presentation.ui.categories.CarouselAdapter
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.*

fun carouselDelegate() = adapterDelegateLayoutContainer<CarouselViewModel, ViewModel>(
    layout = R.layout.item_carousel
) {
    bind {
        val binding = ItemCarouselBinding.bind(itemView)
        val adapter = CarouselAdapter()
        binding.carouselName.text = item.name
        binding.carouselItems.adapter = adapter
        adapter.submitList(item.articles)
    }
}

fun headerDelegate() = adapterDelegateLayoutContainer<HeaderViewModel, ViewModel>(
    layout = R.layout.item_header
) {

    bind {
        val binding = ItemHeaderBinding.bind(itemView)
        binding.headerName.text = item.category.capitalize()
    }
}

fun primaryArticleDelegate() = adapterDelegateLayoutContainer<ArticleViewModel, ViewModel>(
    layout = R.layout.item_main_article,
    on = { _, _, position -> position == 0 }
) {

    bind {
        val binding = ItemMainArticleBinding.bind(itemView)
        binding.mainArticleImage.loadUrl(item.urlToImage)
        binding.mainArticleTitle.text = item.title
        binding.mainArticleUpdated.text = item.publishedAt

        item.setClickListenerFor(itemView)
    }
}

fun secondaryArticleDelegate() =
    adapterDelegateLayoutContainer<ArticleViewModel, ViewModel>(R.layout.item_article) {

        bind {
            val binding = ItemArticleBinding.bind(itemView)
            binding.articleImage.loadUrl(item.urlToImage)
            binding.articleTitle.text = item.title
            binding.articleUpdated.text = item.publishedAt

            item.setClickListenerFor(itemView)
        }
    }

fun videoDelegate() =
    adapterDelegateLayoutContainer<PlaylistItemViewModel, ViewModel>(R.layout.item_video) {
        bind {
            val binding = ItemVideoBinding.bind(itemView)
            binding.videoImage.loadUrl(item.thumbnail)
            binding.videoTitle.text = item.title
            binding.videoUpdated.text = item.publishedAt
            binding.root.setOnClickListener {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(context.getString(R.string.youtube_url, item.videoId))
                    )
                )
            }
        }
    }
