package info.tommarsh.mynews.home.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.core.ui.ListLoadStateAdapter
import info.tommarsh.mynews.core.util.createDiffItemCallback
import info.tommarsh.mynews.home.model.ArticleViewModel
import info.tommarsh.mynews.home.ui.onClickEvent
import info.tommarsh.mynews.home.databinding.ItemCarouselBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

internal class CarouselAdapter(
    private val lifecycle: Lifecycle,
    private val onClickEvent: onClickEvent,
    private val pagingFactory: (category: String) -> Flow<PagingData<ArticleViewModel>>
) : ListAdapter<CategoryViewModel, CarouselViewHolder>(DIFFER) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding =
            ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding, lifecycle, onClickEvent, pagingFactory)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: CarouselViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    companion object {
        private val DIFFER = createDiffItemCallback<CategoryViewModel> { old, new ->
            old.name == new.name
        }
    }
}

internal class CarouselViewHolder(
    private val binding: ItemCarouselBinding,
    private val lifecycle: Lifecycle,
    private val onClickEvent: onClickEvent,
    private val pagingFactory: (category: String) -> Flow<PagingData<ArticleViewModel>>
) :
    RecyclerView.ViewHolder(binding.root) {

    private var job: Job? = null

    fun bind(carousel: CategoryViewModel) {
        val adapter = CarouselItemAdapter(onClickEvent)
        binding.carouselName.text = carousel.name
        binding.carouselItems.adapter = adapter.withLoadStateFooter(ListLoadStateAdapter {
            adapter.retry()
        })
        binding.carouselItems.itemAnimator = null
        job = lifecycle.coroutineScope.launchWhenCreated {
            pagingFactory(carousel.id).collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    fun unbind() {
        job?.cancel()
    }
}