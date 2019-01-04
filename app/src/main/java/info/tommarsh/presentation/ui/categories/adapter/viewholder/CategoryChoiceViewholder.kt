package info.tommarsh.presentation.ui.categories.adapter.viewholder

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.core.extensions.inflate
import info.tommarsh.presentation.R
import info.tommarsh.presentation.model.CategoryViewModel
import kotlinx.android.synthetic.main.item_category_choice.view.*

class CategoryChoiceViewholder(
    parent: ViewGroup,
    private val onClick: (category: CategoryViewModel) -> Unit
) :
    RecyclerView.ViewHolder(parent.inflate(R.layout.item_category_choice)) {

    fun bind(category: CategoryViewModel) = with(itemView) {
        category_choice_name.text = category.name
        category_choice_image.setImageDrawable(getIcon(category.selected))
        category_choice_root.setOnClickListener { _ -> onClick(category) }
    }

    private fun getIcon(selected: Boolean) =
        ContextCompat.getDrawable(
            itemView.context,
            if (selected) R.drawable.ic_minus_circle_outline
            else R.drawable.ic_plus_circle_outline
        )
}