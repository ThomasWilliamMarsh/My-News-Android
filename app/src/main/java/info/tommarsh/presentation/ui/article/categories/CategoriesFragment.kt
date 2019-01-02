package info.tommarsh.presentation.ui.article.categories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import info.tommarsh.core.extensions.makeVisible
import info.tommarsh.domain.model.CategoryModel
import info.tommarsh.presentation.NewsApp.Companion.graph
import info.tommarsh.presentation.R
import info.tommarsh.presentation.ui.base.BaseFragment
import info.tommarsh.presentation.ui.categories.CategoryChoiceActivity
import kotlinx.android.synthetic.main.layout_add_categories.*

class CategoriesFragment : BaseFragment() {

    private val viewModel: CategoriesViewModel by lazy {
        ViewModelProviders.of(this, factory).get(CategoriesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        graph(context!!).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_categories, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_categories_button.setOnClickListener { _ ->
            startActivity(
                Intent(
                    context,
                    CategoryChoiceActivity::class.java
                )
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getSelectedCategories().observe(viewLifecycleOwner, Observer(::onSelectedCategories))
    }

    fun onSelectedCategories(categories: List<CategoryModel>) {
        if (categories.isEmpty()) {
            add_categories_root.makeVisible()
        }
    }
}