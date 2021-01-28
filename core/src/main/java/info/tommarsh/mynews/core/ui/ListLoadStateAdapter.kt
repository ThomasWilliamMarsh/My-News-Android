package info.tommarsh.mynews.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.mynews.core.R
import info.tommarsh.mynews.core.databinding.ItemLoadStateBinding

class ListLoadStateAdapter(private val onRetry: () -> Unit) :
    LoadStateAdapter<LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding, onRetry)
    }

    override fun getStateViewType(loadState: LoadState): Int {
        return R.layout.item_load_state
    }
}

class LoadStateViewHolder(
    private val binding: ItemLoadStateBinding,
    onRetry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { onRetry() }
    }

    fun bind(loadState: LoadState) {

        if (loadState is LoadState.Error) {
            binding.errorMessage.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMessage.isVisible = loadState !is LoadState.Loading
    }
}