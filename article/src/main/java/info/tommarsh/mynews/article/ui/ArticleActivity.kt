package info.tommarsh.mynews.article.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import info.tommarsh.mynews.article.di.ArticleInjector.inject
import info.tommarsh.mynews.core.util.contentBehindStatusBar
import info.tommarsh.mynews.core.util.loadUrl
import marsh.tommarsh.article.R
import marsh.tommarsh.article.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {

    private val binding by lazy { ActivityArticleBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        contentBehindStatusBar()
        setContentView(binding.root)

        binding.articleText.text = getString(R.string.lorem_ipsum)
        binding.articleTitle.text = intent.getStringExtra("title")
        binding.articleMainPicture.loadUrl(intent.getStringExtra("url"))
    }
}
