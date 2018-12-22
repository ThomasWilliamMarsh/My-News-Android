package info.tommarsh.presentation.ui.article.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import info.tommarsh.core.network.NetworkException
import info.tommarsh.presentation.App
import info.tommarsh.presentation.R
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.ui.article.BaseFragment
import info.tommarsh.presentation.ui.article.top.adapter.TopNewsAdapter
import kotlinx.android.synthetic.main.fragment_top_news.*

class TopNewsFragment : BaseFragment() {

    private val adapter = TopNewsAdapter()

    private val viewModel: TopNewsViewModel by lazy {
        ViewModelProviders.of(this, factory).get(TopNewsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.graph(context!!).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_top_news, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        top_news_recycler_view.adapter = adapter
        top_news_recycler_view.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        refresh_top_news.setOnRefreshListener { viewModel.refreshBreakingNews() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getBreakingNews().observe(viewLifecycleOwner, Observer(::onArticlesReceived))
        viewModel.getErrors().observe(viewLifecycleOwner, Observer(::onError))
    }

    private fun onArticlesReceived(articles: List<ArticleViewModel>?) {
        adapter.submitList(articles)
        refresh_top_news.isRefreshing = false
    }

    private fun onError(error: NetworkException) {
        refresh_top_news.isRefreshing = false
    }
}