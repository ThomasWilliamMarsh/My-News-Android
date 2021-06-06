package info.tommarsh.mynews.categories.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.categories.databinding.FragmentCategoryChoiceBinding
import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.categories.ui.adapter.CategoryChoiceAdapter
import info.tommarsh.mynews.core.util.doOnInsets
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CategoryChoiceFragment : Fragment() {

    private lateinit var binding: FragmentCategoryChoiceBinding

    private val viewModel by viewModels<CategoryChoiceViewModel>()

    private val adapter = CategoryChoiceAdapter(::onCategorySelected)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpRecyclerView()
        setUpWindowInsets()
    }

    private fun setUpRecyclerView() {
        binding.activityChoiceRecyclerView.adapter = adapter
        binding.activityChoiceRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                VERTICAL
            )
        )
    }

    private fun setUpViewModel() {
        lifecycleScope.launchWhenResumed {
            viewModel.categories.collect { categories -> onCategories(categories) }
        }
    }

    private fun onCategories(categories: List<CategoryViewModel>) {
        adapter.submitList(categories)
    }

    private fun onCategorySelected(category: CategoryViewModel, selected: Boolean) {
        viewModel.updateCategory(category.copy(selected = selected))
    }

    private fun setUpWindowInsets() {
        binding.root.doOnInsets { systemBarInsets, _ ->
            binding.choiceAppBar.updatePadding(top = systemBarInsets.top)
        }
    }
}