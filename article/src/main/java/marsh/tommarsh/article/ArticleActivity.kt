package marsh.tommarsh.article

import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.WindowManager
import info.tommarsh.core.extensions.contentBehindStatusBar
import info.tommarsh.core.extensions.loadUrl
import info.tommarsh.presentation.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_article.*
import marsh.tommarsh.di.ArticleInjector.inject

class ArticleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        contentBehindStatusBar()
        setContentView(R.layout.activity_article)

        observeNightMode()

        article_text.text = getString(R.string.lorem_ipsum)
        article_title.text = intent.getStringExtra("title")
        article_main_picture.loadUrl(intent.getStringExtra("url"))
    }
}
