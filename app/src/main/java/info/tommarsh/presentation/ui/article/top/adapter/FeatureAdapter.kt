package info.tommarsh.presentation.ui.article.top.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.ui.article.top.adapter.viewholder.FeatureViewHolder
import info.tommarsh.presentation.utils.extensions.getDiffUtilItemCallback

class FeatureAdapter : ListAdapter<ArticleViewModel, FeatureViewHolder>(
    callback
) {

    companion object {
        private val callback =
            getDiffUtilItemCallback<ArticleViewModel> { old, new -> true }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}