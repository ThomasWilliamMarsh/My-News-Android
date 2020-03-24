package info.tommarsh.mynews.categories.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import info.tommarsh.categories.databinding.ActivityCategoryChoiceBinding
import info.tommarsh.mynews.categories.di.inject
import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.categories.ui.adapter.CategoryChoiceAdapter
import info.tommarsh.mynews.core.util.ViewModelFactory
import kotlinx.android.synthetic.main.activity_category_choice.*
import javax.inject.Inject

class CategoryChoiceActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val binding by lazy { ActivityCategoryChoiceBinding.inflate(layoutInflater) }

    private val viewModel: CategoryChoiceViewModel by lazy {
        ViewModelProviders.of(this, factory).get(CategoryChoiceViewModel::class.java)
    }

    private val adapter = CategoryChoiceAdapter(::onCategorySelected)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        setContentView(binding.root)
        setUpViewModel()
        setUpUi()
    }

    private fun setUpUi() {
        setSupportActionBar(activity_choice_toolbar)
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
        category.selected = selected
        viewModel.updateCategory(category)
    }
}