package info.tommarsh.mynews.presentation.ui.categories

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.model.ViewModel
import info.tommarsh.mynews.presentation.ui.ArticleFragment
import info.tommarsh.mynews.presentation.ui.top.TopNewsAdapter
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.FragmentCategoriesBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : ArticleFragment() {

    lateinit var binding: FragmentCategoriesBinding

    private val viewModel by viewModels<CategoriesViewModel>()

    private lateinit var adapter : CarouselAdapter

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
        adapter = CarouselAdapter(lifecycle, viewModel::getArticlesForCategory)
        binding.myNewsRecyclerView.adapter = adapter
        binding.myNewsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.refreshMyNews.setOnRefreshListener {
            binding.refreshMyNews.isRefreshing = true
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.selectedCategories.observe(viewLifecycleOwner) { categories ->
            adapter.submitList(categories)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.categories_toolbar_menu, menu)
    }
}