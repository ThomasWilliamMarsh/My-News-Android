package info.tommarsh.mynews.presentation.ui.categories

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.model.ViewModel
import info.tommarsh.mynews.presentation.ui.ArticleFragment
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
        binding.myNewsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.refreshMyNews.setOnRefreshListener {
            binding.refreshMyNews.isRefreshing = true
            viewModel.refreshFeed()
        }

        viewModel.refreshFeed()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshFeed()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.categories_toolbar_menu, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.feed.observe(viewLifecycleOwner, Observer(::onArticleCategories))
    }

    private fun onArticleCategories(articles: List<ViewModel>) {
        binding.addCategories.root.isVisible = articles.isEmpty()
        binding.refreshMyNews.isRefreshing = false
        adapter.items = articles
    }
}