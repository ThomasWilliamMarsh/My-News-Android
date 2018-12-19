package info.tommarsh.presentation.ui.article

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import info.tommarsh.presentation.R
import info.tommarsh.presentation.ui.article.top.TopNewsViewModel
import info.tommarsh.presentation.ui.base.BaseActivity

class ArticlesActivity : BaseActivity() {

    private val viewModel: TopNewsViewModel by lazy {
        ViewModelProviders.of(this, factory).get(TopNewsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component.inject(this)

        viewModel.getBreakingNews().observe(this, Observer {
            val items = it
            print(items.toString())
        })

        viewModel.errors.observe(this, Observer {
            val error = it
            print(error.toString())
        })
    }
}
