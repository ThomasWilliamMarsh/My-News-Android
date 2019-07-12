package marsh.tommarsh.article

import android.os.Bundle
import info.tommarsh.core.extensions.loadUrl
import info.tommarsh.presentation.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_article.*
import marsh.tommarsh.di.ArticleInjector.inject

class ArticleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        setContentView(R.layout.activity_article)
        observeNightMode()
        article_text.text = getString(R.string.lorem_ipsum)
        article_main_picture.loadUrl(intent.getStringExtra("url"))
    }
}
