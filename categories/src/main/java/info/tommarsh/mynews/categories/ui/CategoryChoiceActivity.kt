package info.tommarsh.mynews.categories.ui


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.categories.databinding.ActivityCategoryChoiceBinding
import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.categories.ui.adapter.CategoryChoiceAdapter
import kotlinx.android.synthetic.main.activity_category_choice.*

@AndroidEntryPoint
class CategoryChoiceActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCategoryChoiceBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<CategoryChoiceViewModel>()

    private val adapter = CategoryChoiceAdapter(::onCategorySelected)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpViewModel()
        setUpUi()
    }

    private fun setUpUi() {
        setSupportActionBar(activity_choice_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.activityChoiceRecyclerView.adapter = adapter
        binding.activityChoiceRecyclerView.addItemDecoration(DividerItemDecoration(this, VERTICAL))
    }

    private fun setUpViewModel() {
        viewModel.categories.observe(this, ::onCategories)
    }

    private fun onCategories(categories: List<CategoryViewModel>) {
        adapter.submitList(categories)
    }

    private fun onCategorySelected(category: CategoryViewModel, selected: Boolean) {
        viewModel.updateCategory(category.copy(selected = selected))
    }
}