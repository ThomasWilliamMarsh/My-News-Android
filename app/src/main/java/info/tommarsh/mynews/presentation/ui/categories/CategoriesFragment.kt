package info.tommarsh.mynews.presentation.ui.categories

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.core.model.ViewModel
import info.tommarsh.mynews.core.util.makeGone
import info.tommarsh.mynews.core.util.makeVisible
import info.tommarsh.mynews.presentation.ui.ArticleFragment
import info.tommarsh.mynews.presentation.ui.categories.CategoriesAdapter.Companion.TYPE_HEADER
import info.tommarsh.presentation.R
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.layout_add_categories.*

class CategoriesFragment : ArticleFragment() {

    private val adapter = CategoriesAdapter()

    private val viewModel: CategoriesViewModel by lazy {
        ViewModelProviders.of(this, factory).get(CategoriesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentGraph.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_categories, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_categories_button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.navigation_choice))
        my_news_recycler_view.adapter = adapter
        my_news_recycler_view.layoutManager = setLayoutManager()
        refresh_my_news.setOnRefreshListener {
            refresh_my_news.isRefreshing = true
            viewModel.refreshFeed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.categories_toolbar_menu, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.feed.observe(viewLifecycleOwner, Observer(::onArticleCategories))
        viewModel.selectedCategories.observe(viewLifecycleOwner, Observer(::onSelectedCategories))
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshFeed()
    }

    private fun onSelectedCategories(categories: List<CategoryModel>) {
        if (categories.isEmpty()) {
            add_categories_root.makeVisible()
        } else {
            add_categories_root.makeGone()
            refresh_my_news.isRefreshing = true
        }
        viewModel.refreshFeed()
    }

    private fun onArticleCategories(articles: List<ViewModel>) {
        refresh_my_news.isRefreshing = false
        adapter.items = articles
    }

    private fun setLayoutManager() = GridLayoutManager(context, 2).apply {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (adapter.getItemViewType(position)) {
                TYPE_HEADER -> 2
                else -> 1
            }
        }
    }
}