package info.tommarsh.mynews.presentation.ui.top

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.consume
import info.tommarsh.mynews.core.util.snack
import info.tommarsh.mynews.presentation.di.inject
import info.tommarsh.mynews.presentation.model.ArticleViewModel
import info.tommarsh.mynews.presentation.ui.ArticleFragment
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.FragmentTopNewsBinding
import javax.inject.Inject

class TopNewsFragment : ArticleFragment() {

    private lateinit var binding: FragmentTopNewsBinding

    @Inject
    lateinit var sharedPreferencesRepository: PreferencesRepository

    private val adapter = TopNewsAdapter()

    private val viewModel: TopNewsViewModel by lazy {
        ViewModelProviders.of(this, factory).get(TopNewsViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTopNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topNewsRecyclerView.adapter = adapter
        binding.refreshTopNews.setOnRefreshListener { viewModel.refreshBreakingNews() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.articles.observe(viewLifecycleOwner, Observer(::onArticlesReceived))
        viewModel.errors.observe(viewLifecycleOwner, Observer(::onError))
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val dayNightItem = menu.findItem(R.id.action_day_night)
        dayNightItem?.setIcon(
            when (sharedPreferencesRepository.getNightMode()) {
                AppCompatDelegate.MODE_NIGHT_YES -> R.drawable.ic_outline_day
                else -> R.drawable.ic_outline_night
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_news_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_day_night -> consume { sharedPreferencesRepository.toggleNightMode() }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onArticlesReceived(articles: List<ArticleViewModel>?) {
        adapter.items = articles
        binding.refreshTopNews.isRefreshing = false
    }

    private fun onError(error: NetworkException) {
        binding.refreshTopNews.isRefreshing = false
        binding.refreshTopNews.snack(error.localizedMessage)
    }
}