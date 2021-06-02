package info.tommarsh.mynews.home.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.dynamicfeatures.fragment.ui.AbstractProgressFragment
import info.tommarsh.mynews.home.R
import info.tommarsh.mynews.home.databinding.FragmentArticleProgressBinding

class ArticleProgressFragment : AbstractProgressFragment() {

    private lateinit var binding: FragmentArticleProgressBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCancelled() {
        showOnWebOption()
    }

    override fun onFailed(errorCode: Int) {
        showOnWebOption()
    }

    override fun onProgress(status: Int, bytesDownloaded: Long, bytesTotal: Long) {
        val progress = (bytesDownloaded / bytesTotal) * 100
        binding.articleProgressBar.progress = progress.toInt()
    }

    private fun showOnWebOption() {
        binding.articleProgressViewOnWeb.isVisible = true
        binding.articleProgressBar.isVisible = false
        binding.articleProgressText.text = getString(R.string.article_progress_error_messge)
    }
}