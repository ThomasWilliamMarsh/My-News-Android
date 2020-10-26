package info.tommarsh.mynews.article.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitcompat.SplitCompat
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.util.contentBehindStatusBar
import info.tommarsh.mynews.core.util.loadUrl
import marsh.tommarsh.article.R
import marsh.tommarsh.article.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {

    private val binding by lazy { ActivityArticleBinding.inflate(layoutInflater) }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.installActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentBehindStatusBar()
        setContentView(binding.root)

        binding.articleText.text = getString(R.string.lorem_ipsum)
        binding.articleTitle.text = intent.getStringExtra("title")
        binding.articleMainPicture.loadUrl(intent.getStringExtra("url"))
    }
}
