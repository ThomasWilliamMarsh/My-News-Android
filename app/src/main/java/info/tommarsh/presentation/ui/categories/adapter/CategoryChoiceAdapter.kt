package info.tommarsh.presentation.ui.categories.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import info.tommarsh.core.extensions.getDiffUtilItemCallback
import info.tommarsh.presentation.model.CategoryViewModel
import info.tommarsh.presentation.ui.categories.adapter.viewholder.CategoryChoiceViewholder

class CategoryChoiceAdapter(private val onClick: (category: CategoryViewModel) -> Unit) :
    ListAdapter<CategoryViewModel, CategoryChoiceViewholder>(callback) {

    companion object {
        val callback = getDiffUtilItemCallback<CategoryViewModel> { old, new -> old.id == new.id }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryChoiceViewholder(parent, onClick)

    override fun onBindViewHolder(holder: CategoryChoiceViewholder, position: Int) {
        holder.bind(getItem(position))
    }
}