package info.tommarsh.presentation.ui.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import info.tommarsh.core.extensions.makeGone
import info.tommarsh.core.extensions.makeVisible
import info.tommarsh.core.extensions.service
import info.tommarsh.core.extensions.snack
import info.tommarsh.core.network.NetworkException
import info.tommarsh.presentation.App
import info.tommarsh.presentation.R
import info.tommarsh.presentation.ViewModelFactory
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.ui.search.adapter.SearchAdapter
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject


class SearchActivity : AppCompatActivity() {

    private val adapter = SearchAdapter()

    @Inject
    protected lateinit var factory: ViewModelFactory

    private val viewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(search_toolbar)
        App.graph(this).inject(this)

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
        viewModel.getArticlesObservable().observe(this, Observer(::onSearchResults))
        viewModel.getErrors().observe(this, Observer(::onError))
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

    private fun onSearchResults(results: List<ArticleViewModel>) {
        search_progress.makeGone()
        adapter.submitList(results)
    }

    private fun onError(exception: NetworkException) {
        search_progress.makeGone()
        search_root.snack(exception.localizedMessage)
    }
}