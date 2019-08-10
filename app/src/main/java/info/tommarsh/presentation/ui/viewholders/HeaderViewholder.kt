package info.tommarsh.presentation.ui.viewholders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.core.extensions.inflate
import info.tommarsh.presentation.R
import info.tommarsh.presentation.model.HeaderViewModel
import kotlinx.android.synthetic.main.item_header.view.*

class HeaderViewholder(parent: ViewGroup) :
    RecyclerView.ViewHolder(parent.inflate(R.layout.item_header)) {

    fun bind(category: HeaderViewModel) = with(itemView) {
        header_name.text = category.category.capitalize()
    }
}