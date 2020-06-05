package info.tommarsh.mynews.onboarding.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.mynews.onboarding.databinding.ItemChoiceBinding
import info.tommarsh.mynews.onboarding.model.ChoiceModel

internal class ChoiceViewholder(private val binding: ItemChoiceBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(choice: ChoiceModel) {
        binding.choiceName.text = choice.name
    }
}