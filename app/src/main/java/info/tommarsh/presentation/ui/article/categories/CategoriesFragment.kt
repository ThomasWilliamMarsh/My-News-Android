package info.tommarsh.presentation.ui.article.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import info.tommarsh.core.ViewModel
import info.tommarsh.core.extensions.makeVisible
import info.tommarsh.domain.model.CategoryModel
import info.tommarsh.presentation.NewsApp.Companion.graph
import info.tommarsh.presentation.R
import info.tommarsh.presentation.ui.article.categories.adapter.CategoriesAdapter
import info.tommarsh.presentation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.layout_add_categories.*

class CategoriesFragment : BaseFragment() {

    private val adapter = CategoriesAdapter()

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
        add_categories_button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.navigation_choice))
        my_news_recycler_view.adapter = adapter
        my_news_recycler_view.layoutManager = GridLayoutManager(context, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapter.getItemViewType(position)) {
                        CategoriesAdapter.TYPE_HEADER -> 2
                        else -> 1
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getSelectedCategories().observe(viewLifecycleOwner, Observer(::onSelectedCategories))
        viewModel.getFeed().observe(viewLifecycleOwner, Observer(::onArticleCategories))
    }

    private fun onSelectedCategories(categories: List<CategoryModel>) {
        if (categories.isEmpty()) {
            add_categories_root.makeVisible()
        } else {
            viewModel.refreshFeed(categories)
        }
    }

    private fun onArticleCategories(articles: List<ViewModel>) {
        adapter.submitList(articles)
    }
}