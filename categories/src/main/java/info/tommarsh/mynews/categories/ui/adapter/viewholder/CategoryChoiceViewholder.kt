package info.tommarsh.mynews.categories.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.categories.databinding.ItemCategoryChoiceBinding
import info.tommarsh.mynews.categories.model.CategoryViewModel
import kotlinx.android.synthetic.main.item_category_choice.view.*

class CategoryChoiceViewholder(
    private val binding: ItemCategoryChoiceBinding,
    private val onClick: (category: CategoryViewModel, selected: Boolean) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(category: CategoryViewModel) = with(itemView) {
        binding.categoryChoiceName.text = category.name
        binding.categoryChoiceCheckbox.isChecked = category.selected
        binding.categoryChoiceCheckbox.setOnCheckedChangeListener { _, selected ->
            onClick(
                category,
                selected
            )
        }
        binding.root.setOnClickListener {
            category_choice_checkbox.isChecked = !category_choice_checkbox.isChecked
        }
    }
}