package info.tommarsh.presentation.ui.article.categories.adapter.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.core.extensions.inflate
import info.tommarsh.presentation.R
import info.tommarsh.presentation.model.CategoryHeaderViewModel
import kotlinx.android.synthetic.main.item_category_header.view.*

class CategoryHeaderViewholder(parent: ViewGroup) :
    RecyclerView.ViewHolder(parent.inflate(R.layout.item_category_header)) {

    fun bind(category: CategoryHeaderViewModel) = with(itemView) {
        category_header_name.text = category.category.capitalize()
    }
}