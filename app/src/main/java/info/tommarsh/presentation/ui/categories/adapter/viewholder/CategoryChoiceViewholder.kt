package info.tommarsh.presentation.ui.categories.adapter.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.core.extensions.inflate
import info.tommarsh.presentation.R
import info.tommarsh.presentation.model.CategoryViewModel
import kotlinx.android.synthetic.main.item_category_choice.view.*

class CategoryChoiceViewholder(
    parent: ViewGroup,
    private val onClick: (category: CategoryViewModel, selected: Boolean) -> Unit
) :
    RecyclerView.ViewHolder(parent.inflate(R.layout.item_category_choice)) {

    fun bind(category: CategoryViewModel) = with(itemView) {
        category_choice_name.text = category.name
        category_choice_checkbox.isChecked = category.selected
        category_choice_checkbox.setOnCheckedChangeListener { _, selected -> onClick(category, selected) }
    }
}