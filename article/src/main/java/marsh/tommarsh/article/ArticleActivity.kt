package marsh.tommarsh.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import info.tommarsh.core.extensions.contentBehindStatusBar
import info.tommarsh.core.extensions.loadUrl
import info.tommarsh.core.extensions.observeNightMode
import info.tommarsh.core.repository.PreferencesRepository
import kotlinx.android.synthetic.main.activity_article.*
import marsh.tommarsh.di.ArticleInjector.inject
import javax.inject.Inject

class ArticleActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferencesRepository: PreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        contentBehindStatusBar()
        setContentView(R.layout.activity_article)

        observeNightMode(sharedPreferencesRepository)

        article_text.text = getString(R.string.lorem_ipsum)
        article_title.text = intent.getStringExtra("title")
        article_main_picture.loadUrl(intent.getStringExtra("url"))
    }
}
