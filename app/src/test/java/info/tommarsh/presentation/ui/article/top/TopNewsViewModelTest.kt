package info.tommarsh.presentation.ui.article.top

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.presentation.model.MockModelProvider
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TopNewsViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val articlesRepository = mock<ArticleRepository>()
    private val mapper = mock<ArticleViewModelMapper> {
        on { map(MockModelProvider.articleModel) }.thenReturn(MockModelProvider.articleViewModel)
    }
    private val topNewsViewModel = TopNewsViewModel(articlesRepository, mapper)

    @Test
    fun `Get breaking news`() {

        topNewsViewModel.getArticlesObservable()

        verify(articlesRepository).getBreakingNews("")
    }

    @Test
    fun `Get errors`() {

        topNewsViewModel.getErrors()

        verify(articlesRepository).errors
    }

    @Test
    fun `Refresh breaking news`() {

    }
}