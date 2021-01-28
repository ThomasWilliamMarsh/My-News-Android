package info.tommarsh.mynews.article.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import info.tommarsh.mynews.core.util.loadUrl
import marsh.tommarsh.article.R
import marsh.tommarsh.article.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {

    private lateinit var binding : FragmentArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            binding.articleTitle.text = bundle.getString("title")
            binding.articleMainPicture.loadUrl(bundle.getString("url").orEmpty())
            binding.articleText.text = getString(R.string.lorem_ipsum)
        } ?: findNavController().popBackStack()
    }
}
