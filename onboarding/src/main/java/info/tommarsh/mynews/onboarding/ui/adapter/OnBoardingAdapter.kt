package info.tommarsh.mynews.onboarding.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import info.tommarsh.mynews.core.util.getDiffUtilItemCallback
import info.tommarsh.mynews.onboarding.databinding.ItemChoiceBinding
import info.tommarsh.mynews.onboarding.model.ChoiceModel

internal class OnBoardingAdapter : ListAdapter<ChoiceModel, ChoiceViewholder>(CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceViewholder {
        val binding = ItemChoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChoiceViewholder(binding)
    }

    override fun onBindViewHolder(holder: ChoiceViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val CALLBACK =
            getDiffUtilItemCallback<ChoiceModel> { old, new -> old.id == new.id }
    }
}