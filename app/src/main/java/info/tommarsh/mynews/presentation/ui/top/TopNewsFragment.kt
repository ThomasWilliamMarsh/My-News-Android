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
import info.tommarsh.mynews.presentation.di.HomeComponentProvider
import info.tommarsh.mynews.presentation.model.ArticleViewModel
import info.tommarsh.mynews.presentation.ui.ArticleFragment
import info.tommarsh.presentation.R
import kotlinx.android.synthetic.main.fragment_top_news.*
import javax.inject.Inject

class TopNewsFragment : ArticleFragment() {

    @Inject
    lateinit var sharedPreferencesRepository: PreferencesRepository

    private val adapter = TopNewsAdapter()

    private val viewModel: TopNewsViewModel by lazy {
        ViewModelProviders.of(this, factory).get(TopNewsViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as HomeComponentProvider)
            .homeComponent()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_top_news, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        top_news_recycler_view.adapter = adapter
        refresh_top_news.setOnRefreshListener { viewModel.refreshBreakingNews() }
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
        refresh_top_news.isRefreshing = false
    }

    private fun onError(error: NetworkException) {
        refresh_top_news.isRefreshing = false
        refresh_top_news.snack(error.localizedMessage)
    }
}