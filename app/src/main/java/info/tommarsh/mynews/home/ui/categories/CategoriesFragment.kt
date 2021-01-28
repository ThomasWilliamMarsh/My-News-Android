package info.tommarsh.mynews.home.ui.categories

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.navigator.ClickEvent
import info.tommarsh.mynews.home.ui.NavigationViewModel
import info.tommarsh.mynews.home.R
import info.tommarsh.mynews.home.databinding.FragmentCategoriesBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    lateinit var binding: FragmentCategoriesBinding

    private val viewModel by viewModels<CategoriesViewModel>()

    private val navigationViewModel by activityViewModels<NavigationViewModel>()

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
        adapter = CarouselAdapter(
            lifecycle = lifecycle,
            onClickEvent = { event: ClickEvent -> navigationViewModel.dispatchClick(event) },
            pagingFactory = viewModel::getArticlesForCategory
        )
        binding.myNewsRecyclerView.adapter = adapter
        binding.myNewsRecyclerView.itemAnimator = null
        binding.myNewsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.addCategories.addCategoriesButton.setOnClickListener {
            navigationViewModel.dispatchClick(ClickEvent.Categories)
        }
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
            R.id.action_search -> navigationViewModel.dispatchClick(ClickEvent.Search)
            R.id.action_edit -> navigationViewModel.dispatchClick(ClickEvent.Categories)
        }
        return true
    }
}