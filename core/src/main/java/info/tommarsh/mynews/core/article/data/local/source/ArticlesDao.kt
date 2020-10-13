package info.tommarsh.mynews.core.article.data.local.source

import androidx.paging.PagingSource
import androidx.room.*
import info.tommarsh.mynews.core.article.data.local.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(vararg articles: Article)

    @Query("SELECT COUNT(*) FROM ARTICLE_TABLE WHERE category == 'top-news'")
    suspend fun getBreakingArticlesOffset() : Int

    @Query("SELECT * FROM ARTICLE_TABLE WHERE category == 'top-news'")
    fun getBreakingArticles(): PagingSource<Int, Article>

    @Query("SELECT * FROM ARTICLE_TABLE WHERE category != 'top-news'")
    fun getFeed(): Flow<List<Article>>

    @Query("DELETE FROM ARTICLE_TABLE WHERE category IN (SELECT id FROM CATEGORY_TABLE WHERE selected = 0)")
    suspend fun deleteUnselectedCategories()

    @Query("DELETE FROM ARTICLE_TABLE WHERE category == :category")
    suspend fun deleteCategory(category: String)

    @Query("DELETE FROM ARTICLE_TABLE WHERE category == 'top-news'")
    suspend fun deleteBreakingArticles(): Int

    @Query("DELETE FROM SOURCE_TABLE")
    suspend fun deleteSources(): Int
}