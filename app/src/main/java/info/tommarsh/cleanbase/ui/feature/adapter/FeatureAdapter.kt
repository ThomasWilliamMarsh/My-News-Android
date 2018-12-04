package info.tommarsh.cleanbase.ui.feature.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import info.tommarsh.cleanbase.ui.feature.adapter.viewholder.FeatureViewHolder
import info.tommarsh.cleanbase.ui.feature.model.FeatureViewModel
import info.tommarsh.cleanbase.utils.extensions.getDiffUtilItemCallback

class FeatureAdapter : ListAdapter<FeatureViewModel, FeatureViewHolder>(callback) {

    companion object {
        private val callback = getDiffUtilItemCallback<FeatureViewModel> { old, new -> true }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}