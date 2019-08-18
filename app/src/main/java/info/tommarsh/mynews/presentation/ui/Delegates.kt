package info.tommarsh.mynews.presentation.ui

import android.content.Intent
import android.net.Uri
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import info.tommarsh.mynews.core.ViewModel
import info.tommarsh.mynews.core.extensions.loadUrl
import info.tommarsh.mynews.presentation.model.ArticleViewModel
import info.tommarsh.mynews.presentation.model.HeaderViewModel
import info.tommarsh.mynews.presentation.model.PlaylistItemViewModel
import info.tommarsh.mynews.presentation.setClickListenerFor
import info.tommarsh.presentation.R
import kotlinx.android.synthetic.main.item_article.*
import kotlinx.android.synthetic.main.item_category_article.*
import kotlinx.android.synthetic.main.item_header.*
import kotlinx.android.synthetic.main.item_main_article.*
import kotlinx.android.synthetic.main.item_video.*


fun headerDelegate() = adapterDelegateLayoutContainer<HeaderViewModel, ViewModel>(R.layout.item_header) {

    bind {
        header_name.text = item.category.capitalize()
    }
}

fun primaryArticleDelegate() = adapterDelegateLayoutContainer<ArticleViewModel, ViewModel>(
    layout = R.layout.item_main_article,
    on = { _, _, position -> position == 0 }
) {

    bind {
        main_article_image.loadUrl(item.urlToImage)
        main_article_title.text = item.title
        main_article_updated.text = item.publishedAt

        item.setClickListenerFor(itemView)
    }
}

fun secondaryArticleDelegate() = adapterDelegateLayoutContainer<ArticleViewModel, ViewModel>(R.layout.item_article) {

    bind {
        article_image.loadUrl(item.urlToImage)
        article_title.text = item.title
        article_updated.text = item.publishedAt

        item.setClickListenerFor(itemView)
    }
}

fun categoryArticleDelegate() =
    adapterDelegateLayoutContainer<ArticleViewModel, ViewModel>(R.layout.item_category_article) {

        bind {
            category_article_name.text = item.title
            category_article_updated.text = item.publishedAt
            if (item.urlToImage.isNotEmpty()) {
                category_article_image.loadUrl(item.urlToImage)
            }

            item.setClickListenerFor(itemView)
        }
    }

fun videoDelegate() =
    adapterDelegateLayoutContainer<PlaylistItemViewModel, ViewModel>(R.layout.item_video) {
        bind {
            video_image.loadUrl(item.thumbnail)
            video_title.text = item.title
            video_updated.text = item.publishedAt
            video_root.setOnClickListener {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(context.getString(R.string.youtube_url, item.videoId))
                    )
                )
            }
        }
    }
