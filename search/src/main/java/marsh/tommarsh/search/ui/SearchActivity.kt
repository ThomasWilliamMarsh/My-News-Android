package marsh.tommarsh.search.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import info.tommarsh.core.NetworkException
import info.tommarsh.core.ViewModelFactory
import info.tommarsh.core.extensions.*
import info.tommarsh.core.repository.PreferencesRepository
import marsh.tommarsh.search.ui.adapter.SearchAdapter
import kotlinx.android.synthetic.main.activity_search.*
import marsh.tommarsh.search.R
import marsh.tommarsh.search.di.Injector.inject
import marsh.tommarsh.search.model.SearchItemViewModel
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    private val adapter = SearchAdapter()

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    private val viewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        setContentView(R.layout.activity_search)
        observeNightMode(preferencesRepository)
        setSupportActionBar(search_toolbar)
        setUpViewModel()
        setUpRecyclerView()
        setUpSearchView()
        onIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        onIntent(intent)
    }

    private fun setUpRecyclerView() {
        search_recycler_view.adapter = adapter
    }

    private fun setUpViewModel() {
        viewModel.articles.observe(this, Observer(::onSearchResults))
        viewModel.errors.observe(this, Observer(::onError))
    }

    private fun setUpSearchView() {
        val searchManager = service<SearchManager>(SEARCH_SERVICE)
        search_view.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconified = false
            setIconifiedByDefault(false)
        }
    }

    private fun onIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            search_progress.makeVisible()
            val query = intent.getStringExtra(SearchManager.QUERY)
            viewModel.searchArticles(query)
        }
    }

    private fun onSearchResults(results: List<SearchItemViewModel>) {
        search_progress.makeGone()
        adapter.submitList(results)
    }

    private fun onError(exception: NetworkException) {
        search_progress.makeGone()
        search_root.snack(exception.localizedMessage)
    }
}