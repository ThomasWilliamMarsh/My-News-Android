package info.tommarsh.mynews.article.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import info.tommarsh.mynews.article.di.ArticleInjector.inject
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.contentBehindStatusBar
import info.tommarsh.mynews.core.util.loadUrl
import kotlinx.android.synthetic.main.activity_article.*
import marsh.tommarsh.article.R
import javax.inject.Inject

class ArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        contentBehindStatusBar()
        setContentView(R.layout.activity_article)

        article_text.text = getString(R.string.lorem_ipsum)
        article_title.text = intent.getStringExtra("title")
        article_main_picture.loadUrl(intent.getStringExtra("url"))
    }
}
