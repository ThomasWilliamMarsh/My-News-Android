package info.tommarsh.mynews.presentation.ui.categories

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.core.model.ViewModel
import info.tommarsh.mynews.core.util.makeGone
import info.tommarsh.mynews.core.util.makeVisible
import info.tommarsh.mynews.presentation.ui.ArticleFragment
import info.tommarsh.mynews.presentation.ui.categories.CategoriesAdapter.Companion.TYPE_HEADER
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.FragmentCategoriesBinding

@AndroidEntryPoint
class CategoriesFragment : ArticleFragment() {

    lateinit var binding: FragmentCategoriesBinding

    private val adapter = CategoriesAdapter()

    private val viewModel by viewModels<CategoriesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addCategories.addCategoriesButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.navigation_choice
            )
        )
        binding.myNewsRecyclerView.adapter = adapter
        binding.myNewsRecyclerView.layoutManager = setLayoutManager()
        binding.refreshMyNews.setOnRefreshListener {
            binding.refreshMyNews.isRefreshing = true
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
            binding.addCategories.root.makeVisible()
        } else {
            binding.addCategories.root.makeGone()
            binding.refreshMyNews.isRefreshing = true
        }
        viewModel.refreshFeed()
    }

    private fun onArticleCategories(articles: List<ViewModel>) {
        binding.refreshMyNews.isRefreshing = false
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