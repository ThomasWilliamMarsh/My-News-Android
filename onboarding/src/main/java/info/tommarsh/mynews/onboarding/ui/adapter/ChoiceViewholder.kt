package info.tommarsh.mynews.onboarding.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.mynews.onboarding.databinding.ItemChoiceBinding
import info.tommarsh.mynews.onboarding.model.Choice

internal class ChoiceViewholder(
    private val binding: ItemChoiceBinding,
    private val onCheckedChange: (id: String, checked: Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(choice: Choice) {
        binding.choiceName.text = choice.name
        binding.choiceCheckbox.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange(choice.id, isChecked)
        }
    }

    fun setChecked(checked: Boolean) {
        binding.choiceCheckbox.isChecked = checked
    }
}