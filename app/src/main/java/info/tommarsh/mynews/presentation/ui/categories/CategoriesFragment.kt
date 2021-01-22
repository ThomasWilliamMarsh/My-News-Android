package info.tommarsh.mynews.presentation.ui.categories

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.navigator.ClickDispatcher
import info.tommarsh.mynews.core.navigator.ClickEvent
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.FragmentCategoriesBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    @Inject
    lateinit var dispatcher: ClickDispatcher

    lateinit var binding: FragmentCategoriesBinding

    private val viewModel by viewModels<CategoriesViewModel>()

    private lateinit var adapter: CarouselAdapter

    private var selectedCategoriesJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CarouselAdapter(lifecycle, viewModel::getArticlesForCategory)
        binding.myNewsRecyclerView.adapter = adapter
        binding.myNewsRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        selectedCategoriesJob = lifecycleScope.launchWhenResumed {
            viewModel.selectedCategories.collectLatest { categories ->
                binding.addCategories.root.isVisible = categories.isEmpty()
                binding.myNewsRecyclerView.isVisible = categories.isNotEmpty()
                adapter.submitList(categories)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        selectedCategoriesJob?.cancel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.categories_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> lifecycleScope.launch { dispatcher.dispatch(ClickEvent.Search) }
            R.id.action_edit -> lifecycleScope.launch { dispatcher.dispatch(ClickEvent.Categories) }
        }
        return true
    }
}