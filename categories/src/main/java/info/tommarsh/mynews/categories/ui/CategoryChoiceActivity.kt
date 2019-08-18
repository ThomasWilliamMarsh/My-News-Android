package info.tommarsh.mynews.categories.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import info.tommarsh.categories.R
import info.tommarsh.mynews.categories.di.Injector.inject
import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.categories.ui.adapter.CategoryChoiceAdapter
import info.tommarsh.mynews.core.ViewModelFactory
import info.tommarsh.mynews.core.extensions.observeNightMode
import info.tommarsh.mynews.core.repository.PreferencesRepository
import kotlinx.android.synthetic.main.activity_category_choice.*
import javax.inject.Inject

class CategoryChoiceActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var sharedPreferencesRepository: PreferencesRepository

    private val viewModel: CategoryChoiceViewModel by lazy {
        ViewModelProviders.of(this, factory).get(CategoryChoiceViewModel::class.java)
    }

    private val adapter = CategoryChoiceAdapter(::onCategorySelected)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        setContentView(R.layout.activity_category_choice)
        observeNightMode(sharedPreferencesRepository)
        setUpViewModel()
        setUpUi()
    }

    private fun setUpUi() {
        setSupportActionBar(activity_choice_toolbar)
        activity_choice_recycler_view.adapter = adapter
        activity_choice_recycler_view.addItemDecoration(DividerItemDecoration(this, VERTICAL))
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