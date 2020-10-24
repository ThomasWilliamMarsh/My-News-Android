package info.tommarsh.mynews.onboarding.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import info.tommarsh.mynews.core.util.getDiffUtilItemCallback
import info.tommarsh.mynews.onboarding.databinding.ItemChoiceBinding
import info.tommarsh.mynews.onboarding.model.Choice

internal class ChoiceAdapter : ListAdapter<Choice, ChoiceViewholder>(CALLBACK) {

    var country = "gb"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceViewholder {
        val binding = ItemChoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChoiceViewholder(binding, ::onCheckedChanged)
    }

    override fun onBindViewHolder(holder: ChoiceViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun onCheckedChanged(id: String, isChecked: Boolean) {
        if (isChecked) {
            country = id
        }
    }

    companion object {
        val CALLBACK =
            getDiffUtilItemCallback<Choice> { old, new -> old.id == new.id }
    }
}